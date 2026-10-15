package se.lnu.savereport;

import se.lnu.savechecker.Badge;
import se.lnu.savechecker.Party;
import se.lnu.savechecker.PartyPokemon;
import se.lnu.savechecker.PokedexStatus;
import se.lnu.savechecker.SaveFile;
import se.lnu.savechecker.Trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds the HTML text for the whole report: trainer, badges, party and
 * charts.
 *
 * <p>Given: the loaded save file and the charts as SVG strings. Gives back:
 * the HTML as one string. It does not write any file.
 */
public final class HtmlReportBuilder {

    /**
     * Builds the full report page for one save file.
     *
     * @param saveFile the loaded save file to describe
     * @param levelChart the party level chart as SVG text
     * @param evCharts one EV chart per party Pokémon as SVG text, in party order
     * @return the report as one HTML string
     */
    public String buildHtmlReport(SaveFile saveFile, String levelChart, List<String> evCharts) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n")
            .append("<meta charset=\"UTF-8\">\n")
            .append("<title>Save report</title>\n")
            .append("</head>\n<body>\n");

        html.append(buildTrainerSection(saveFile.getTrainer()));
        html.append(buildPokedexSection(saveFile.getPokedexStatus()));
        html.append(buildPartySection(saveFile.getParty()));
        // Browsers draw <svg> directly inside the page, so the charts go in as they are.
        html.append(levelChart);
        for (String evChart : evCharts) {
            html.append(evChart);
        }

        html.append("</body>\n</html>\n");
        return html.toString();
    }

    /** The trainer's name as the page heading, followed by the badge list (R4, R5). */
    private String buildTrainerSection(Trainer trainer) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(trainer.getName()).append("</h1>\n");

        html.append("<h2>Badges</h2>\n<ul>\n");
        for (Badge badge : Badge.values()) {
            String status = trainer.hasBadge(badge) ? "yes" : "no";
            html.append("<li>").append(badge).append(": ").append(status).append("</li>\n");
        }
        html.append("</ul>\n");
        return html.toString();
    }

    /** How many species the trainer has seen and caught. */
    private String buildPokedexSection(PokedexStatus pokedex) {
        return "<h2>Pokédex</h2>\n"
            + "<p>Seen: " + pokedex.getSeenCount() + "</p>\n"
            + "<p>Caught: " + pokedex.getCaughtCount() + "</p>\n";
    }

    /** One table row per party Pokémon, with its stats, EVs and IVs (R6-R9). */
    private String buildPartySection(Party party) {
        StringBuilder html = new StringBuilder();
        html.append("<h2>Party</h2>\n<table border=\"1\">\n")
            .append(buildTableRow("th", "Nickname", "Level", "Experience",
                "Max HP", "Attack", "Defense", "Speed", "Special",
                "HP EV", "Attack EV", "Defense EV", "Speed EV", "Special EV",
                "Attack IV", "Defense IV", "Speed IV", "Special IV"));

        for (PartyPokemon pokemon : party.getPokemon()) {
            html.append(buildTableRow("td", pokemon.getNickname(),
                pokemon.getLevel(), pokemon.getExperience(),
                pokemon.getMaxHp(), pokemon.getAttack(), pokemon.getDefense(),
                pokemon.getSpeed(), pokemon.getSpecial(),
                pokemon.getHpEv(), pokemon.getAttackEv(), pokemon.getDefenseEv(),
                pokemon.getSpeedEv(), pokemon.getSpecialEv(),
                pokemon.getAttackIv(), pokemon.getDefenseIv(),
                pokemon.getSpeedIv(), pokemon.getSpecialIv()));
        }
        html.append("</table>\n");
        return html.toString();
    }

    /**
     * Builds one table row.
     *
     * @param cellTag {@code "th"} for a header row, {@code "td"} for a data row
     * @param cells the values to put in the row, in order
     */
    private String buildTableRow(String cellTag, Object... cells) {
        StringBuilder row = new StringBuilder("<tr>");
        for (Object cell : cells) {
            row.append("<").append(cellTag).append(">")
                .append(cell)
                .append("</").append(cellTag).append(">");
        }
        return row.append("</tr>\n").toString();
    }
}
