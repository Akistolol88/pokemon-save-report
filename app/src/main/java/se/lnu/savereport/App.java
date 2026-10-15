package se.lnu.savereport;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import se.lnu.savechecker.InvalidSaveFileException;
import se.lnu.savechecker.Party;
import se.lnu.savechecker.SaveFile;

/**
 * Starts the app and runs the main loop: ask for a save file, build its
 * report, and ask again until the user types {@code quit}.
 *
 * <p>This class only connects the other classes. The work happens in
 * {@link ConsoleUi}, {@link SaveFileLoader}, {@link ChartBuilder},
 * {@link HtmlReportBuilder} and {@link ReportFileWriter}.
 */
public final class App {
    private final ConsoleUi consoleUi = new ConsoleUi();
    private final SaveFileLoader loader = new SaveFileLoader();
    private final ChartBuilder chartBuilder = new ChartBuilder();
    private final HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
    private final ReportFileWriter writer = new ReportFileWriter();

    /**
     * Starts the app.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        new App().run();
    }

    /**
     * Shows the welcome message, then makes one report per save file the user
     * enters, until the user types quit (R1, R3, R14).
     *
     * <p>If a report cannot be made, the user gets a short message and is
     * asked for another file instead of the app closing (R12, R13).
     */
    private void run() {
        consoleUi.showWelcomeMessage();

        while (true) {
            String savePath = consoleUi.askForSavePath();
            if (consoleUi.isQuitCommand(savePath)) {
                break;
            }
            try {
                Path reportFile = createReport(savePath);
                consoleUi.showReportWritten(reportFile);
            } catch (NoSuchFileException e) {
                consoleUi.showError("File not found: " + savePath);
            } catch (InvalidSaveFileException e) {
                consoleUi.showError("Not a valid save file: " + e.getMessage());
            } catch (InvalidPathException e) {
                // Thrown for text that cannot be a path at all, like a name with ? or ".
                consoleUi.showError("Not a valid path: " + savePath);
            } catch (IOException e) {
                // Any other file problem, such as a folder instead of a file, or
                // the report folder not being writable.
                consoleUi.showError("Could not read or write a file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads one save file, builds its charts and HTML, and writes the report.
     *
     * @param savePath the path to the save file, as the user typed it
     * @return the path of the written report file
     * @throws NoSuchFileException if there is no file at {@code savePath}
     * @throws InvalidSaveFileException if the file is not a valid save
     * @throws IOException if the save cannot be read or the report cannot be
     *     written
     */
    private Path createReport(String savePath) throws IOException, InvalidSaveFileException {
        SaveFile saveFile = loader.load(savePath);
        Party party = saveFile.getParty();
        String levelChart = chartBuilder.buildLevelChart(party);
        List<String> evCharts = chartBuilder.buildEvCharts(party);

        String html = htmlBuilder.buildHtmlReport(saveFile, levelChart, evCharts);
        return writer.write(html);
    }
}
