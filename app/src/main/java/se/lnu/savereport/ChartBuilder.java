package se.lnu.savereport;

import java.util.ArrayList;
import java.util.List;

import se.lnu.rm222xi.svgcharts.BarChart;
import se.lnu.savechecker.Party;
import se.lnu.savechecker.PartyPokemon;

/**
 * Builds the charts for the report as SVG text, using the svg-charts module.
 *
 * <p>Given: the party from the save file. Gives back: SVG strings. This is the
 * only class that knows about svg-charts.
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

        BarChart levelChart = new BarChart();
        levelChart.setTitle("Level Chart");

        for (PartyPokemon pokemon : party.getPokemon()) {
            pokemonNicknames.add(pokemon.getNickname());
            // svg-charts only takes Double values, and getLevel() gives an int.
            pokemonLevels.add((double) pokemon.getLevel());
        }
        levelChart.setLabels(pokemonNicknames);
        levelChart.setValues(pokemonLevels);
        return levelChart.toSvg();
    }

    /**
     * Builds one bar chart with the five EVs of a single Pokémon (R11).
     *
     * <p>These are Generation 1 EVs ("stat experience"), so each one goes from
     * 0 to 65535. A Pokémon that has never won a battle has 0 in all five.
     *
     * @param pokemon the Pokémon to draw the chart for
     * @return the chart as SVG text, titled with the Pokémon's nickname
     */
    private String buildEvChart(PartyPokemon pokemon) {
        BarChart evChart = new BarChart();
        evChart.setTitle(pokemon.getNickname());
        // The labels and values must be in the same order: label 1 goes with value 1.
        evChart.setLabels(List.of("HP", "Attack", "Defense", "Speed", "Special"));
        evChart.setValues(List.of(
                (double) pokemon.getHpEv(),
                (double) pokemon.getAttackEv(),
                (double) pokemon.getDefenseEv(),
                (double) pokemon.getSpeedEv(),
                (double) pokemon.getSpecialEv()));

        return evChart.toSvg();
    }

    /**
     * Builds an EV chart for every Pokémon in the party, in party order (R11).
     *
     * @param party the party from the save file
     * @return one SVG chart per Pokémon
     */
    public List<String> buildEvCharts(Party party) {
        List<String> evCharts = new ArrayList<>();
        for (PartyPokemon pokemon : party.getPokemon()) {
            evCharts.add(buildEvChart(pokemon));
        }
        return evCharts;
    }
}
