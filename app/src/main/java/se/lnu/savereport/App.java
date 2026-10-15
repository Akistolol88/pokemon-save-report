package se.lnu.savereport;

import se.lnu.savechecker.SaveFile;
import java.nio.file.Path;
import java.util.List;

/**
 * Starts the app and runs the main loop: ask for a save file, build its
 * report, and ask again until the user types {@code quit}.
 *
 * <p>This class only connects the other classes. The work happens in
 * {@link ConsoleUi}, {@link SaveFileLoader}, {@link ChartBuilder},
 * {@link HtmlReportBuilder} and {@link ReportFileWriter}.
 */
public class App {

    /**
     * Runs the app.
     *
     * @param args not used
     * @throws Exception for now, if a file cannot be read, is not a valid save,
     *     or the report cannot be written
     */
    public static void main(String[] args) throws Exception {
        ConsoleUi consoleUi = new ConsoleUi();
        SaveFileLoader loader = new SaveFileLoader();
        ChartBuilder chartBuilder = new ChartBuilder();
        HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
        ReportFileWriter writer = new ReportFileWriter();

        consoleUi.showWelcomeMessage();

        // Repeats until the user types quit (R3, R14).
        while (true) {
            String savePath = consoleUi.askForSavePath();
            if (savePath.equals("quit")) {
                break;
            }

            SaveFile saveFile = loader.load(savePath);

            String levelChart = chartBuilder.buildLevelChart(saveFile.getParty());
            List<String> evCharts = chartBuilder.buildEvCharts(saveFile.getParty());

            String html = htmlBuilder.buildHtmlReport(saveFile, levelChart, evCharts);
            Path reportFile = writer.write(html);

            consoleUi.showReportWritten(reportFile);
        }
    }
}
