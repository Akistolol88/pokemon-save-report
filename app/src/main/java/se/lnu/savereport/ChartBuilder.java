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
    public String buildLevelChart(Party party) {
        List<String> pokemonNicknames = new ArrayList<>();
        List<Double> pokemonLevels = new ArrayList<>();

        BarChart levelChart = new BarChart();
        levelChart.setTitle("Level Chart");
        
        for (PartyPokemon pokemon : party.getPokemon()) {
            pokemonNicknames.add(pokemon.getNickname());
            pokemonLevels.add((double) pokemon.getLevel());
        }
        levelChart.setLabels(pokemonNicknames);
        levelChart.setValues(pokemonLevels);
        return levelChart.toSvg();
    }
}
