package se.lnu.savereport;

import java.util.ArrayList;
import java.util.List;
import se.lnu.rm222xi.svgcharts.BarChart;
import se.lnu.savechecker.Party;
import se.lnu.savechecker.PartyPokemon;

/**
 * Builds the charts for the report as SVG text, using the svg-charts module.
 *
 * <p>Given: the party from the save file. Gives back: SVG strings, or a short
 * "no EVs yet" note where an EV chart would be empty. This is the only class
 * that knows about svg-charts.
 */
public final class ChartBuilder {

    /**
     * Builds one bar chart with the level of every Pokémon in the party, one
     * bar per Pokémon labelled with its nickname (R10).
     *
     * @param party the party from the save file
     * @return the chart as SVG text
     */
    public String buildLevelChart(Party party) {
        List<String> pokemonNicknames = new ArrayList<>();
        List<Double> pokemonLevels = new ArrayList<>();

        for (PartyPokemon pokemon : party.getPokemon()) {
            pokemonNicknames.add(pokemon.getNickname());
            // svg-charts only takes Double values, and getLevel() gives an int.
            pokemonLevels.add((double) pokemon.getLevel());
        }
        return buildBarChart("Party levels", pokemonNicknames, pokemonLevels);
    }

    /**
     * Builds an EV chart for every Pokémon in the party, in party order (R11).
     *
     * @param party the party from the save file
     * @return one entry per Pokémon: an SVG chart, or a "no EVs yet" note
     */
    public List<String> buildEvCharts(Party party) {
        List<String> evCharts = new ArrayList<>();
        for (PartyPokemon pokemon : party.getPokemon()) {
            evCharts.add(buildEvChart(pokemon));
        }
        return evCharts;
    }

    /**
     * Builds one bar chart with the five EVs of a single Pokémon (R11).
     *
     * <p>These are Generation 1 EVs ("stat experience"), so each one goes from
     * 0 to 65535. A Pokémon that has never won a battle has 0 in all five.
     * Then a short note is returned instead, because svg-charts draws an
     * empty chart with a 0 to 1 axis, which looks like a bug.
     *
     * @param pokemon the Pokémon to draw the chart for
     * @return the chart as SVG text, titled with the Pokémon's nickname, or a
     *     {@code <p>} "no EVs yet" note if all five EVs are 0
     */
    private String buildEvChart(PartyPokemon pokemon) {
        if (hasNoEvs(pokemon)) {
            return "<p>" + pokemon.getNickname() + ": no EVs yet</p>\n";
        }
        // The labels and values must be in the same order: label 1 goes with value 1.
        return buildBarChart(pokemon.getNickname(),
                List.of("HP", "Attack", "Defense", "Speed", "Special"),
                List.of(
                        (double) pokemon.getHpEv(),
                        (double) pokemon.getAttackEv(),
                        (double) pokemon.getDefenseEv(),
                        (double) pokemon.getSpeedEv(),
                        (double) pokemon.getSpecialEv()));
    }

    /**
     * Builds a bar chart with svg-charts. Every chart in the report goes
     * through here, so this is the only place that uses {@link BarChart}.
     *
     * @param title the text shown above the chart
     * @param labels the name under each bar
     * @param values the height of each bar, in the same order as the labels
     * @return the chart as SVG text
     */
    private String buildBarChart(String title, List<String> labels, List<Double> values) {
        BarChart chart = new BarChart();
        chart.setTitle(title);
        chart.setLabels(labels);
        chart.setValues(values);
        return chart.toSvg();
    }

    /**
     * Checks if all five EVs of a Pokémon are 0, so there is nothing to draw.
     *
     * @param pokemon the Pokémon to check
     * @return {@code true} if the Pokémon has no EVs at all
     */
    private boolean hasNoEvs(PartyPokemon pokemon) {
        return pokemon.getHpEv() == 0
                && pokemon.getAttackEv() == 0
                && pokemon.getDefenseEv() == 0
                && pokemon.getSpeedEv() == 0
                && pokemon.getSpecialEv() == 0;
    }
}
