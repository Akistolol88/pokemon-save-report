package se.lnu.savereport;

import java.util.List;
import se.lnu.savechecker.Badge;
import se.lnu.savechecker.Party;
import se.lnu.savechecker.PartyPokemon;
import se.lnu.savechecker.PokedexStatus;
import se.lnu.savechecker.SaveFile;
import se.lnu.savechecker.Trainer;

/**
 * Builds the HTML text for the whole report: trainer and trainer ID, badges,
 * Pokédex, party and charts.
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
     * @param evCharts one entry per party Pokémon, in party order: an EV chart
     *     as SVG text, or a "no EVs yet" note as HTML
     * @return the report as one HTML string
     */
    public String buildHtmlReport(SaveFile saveFile, String levelChart, List<String> evCharts) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n")
            .append("<meta charset=\"UTF-8\">\n")
            .append("<title>Save report</title>\n")
            .append("</head>\n<body>\n");

        Trainer trainer = saveFile.getTrainer();

        html.append(buildTrainerSection(trainer));
        html.append(buildPokedexSection(saveFile.getPokedexStatus()));
        html.append(buildPartySection(saveFile.getParty(), trainer.getId()));
        // Browsers draw <svg> directly inside the page, so the charts (and the
        // "no EVs yet" notes, which are already HTML) go in as they are.
        html.append("<h2>Levels</h2>\n").append(levelChart);
        html.append("<h2>Stat Exp (EVs) per Pokémon</h2>\n");
        for (String evChart : evCharts) {
            html.append(evChart);
        }

        html.append("</body>\n</html>\n");
        return html.toString();
    }

    /** The trainer's name as the page heading, their trainer ID and the badge list (R4, R5, R16). */
    private String buildTrainerSection(Trainer trainer) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(trainer.getName()).append("</h1>\n");
        html.append("<p>Trainer ID: ").append(trainer.getId()).append("</p>\n");

        html.append("<h2>Badges</h2>\n<ul>\n");
        for (Badge badge : Badge.values()) {
            String status = trainer.hasBadge(badge) ? "yes" : "no";
            html.append("<li>").append(badge).append(": ").append(status).append("</li>\n");
        }
        html.append("</ul>\n");
        return html.toString();
    }

    /** How many species the trainer has seen and caught (R15). */
    private String buildPokedexSection(PokedexStatus pokedex) {
        return "<h2>Pokédex</h2>\n"
            + "<p>Seen: " + pokedex.getSeenCount() + "</p>\n"
            + "<p>Caught: " + pokedex.getCaughtCount() + "</p>\n";
    }

    /**
     * One table row per party Pokémon, with its stats, EVs and IVs (R6-R9,
     * R16).
     *
     * @param party the Pokémon to list
     * @param trainerId the save owner's trainer ID, used to tell which
     *     Pokémon were traded in
     */
    private String buildPartySection(Party party, int trainerId) {
        StringBuilder html = new StringBuilder();
        html.append("<h2>Party</h2>\n<table border=\"1\">\n")
            .append(buildTableRow("th", "Nickname", "Traded", "Level", "Experience",
                "Max HP", "Attack", "Defense", "Speed", "Special",
                "HP Stat Exp", "Attack Stat Exp", "Defense Stat Exp",
                "Speed Stat Exp", "Special Stat Exp",
                "HP IV", "Attack IV", "Defense IV", "Speed IV", "Special IV"));

        for (PartyPokemon pokemon : party.getPokemon()) {
            String traded = pokemon.getOriginalTrainerId() == trainerId ? "No" : "Yes";
            html.append(buildTableRow("td", pokemon.getNickname(),
                traded,
                pokemon.getLevel(), pokemon.getExperience(),
                pokemon.getMaxHp(), pokemon.getAttack(), pokemon.getDefense(),
                pokemon.getSpeed(), pokemon.getSpecial(),
                pokemon.getHpEv(), pokemon.getAttackEv(), pokemon.getDefenseEv(),
                pokemon.getSpeedEv(), pokemon.getSpecialEv(),
                pokemon.getHpIv(),
                pokemon.getAttackIv(), pokemon.getDefenseIv(),
                pokemon.getSpeedIv(), pokemon.getSpecialIv()));
        }
        html.append("</table>\n")
            // Gen 1 EVs look far too high to players who know the later games (max 252).
            .append("<p>Stat Exp is how Generation 1 stores EVs. Each one goes from 0 to 65535.</p>\n");
        return html.toString();
    }

    /**
     * Builds one table row.
     *
     * @param cellTag {@code "th"} for a header row, {@code "td"} for a data row
     * @param cells the values to put in the row, in order
     * @return the row as HTML, ending with a line break
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
