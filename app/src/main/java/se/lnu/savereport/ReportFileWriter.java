package se.lnu.savereport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes the finished HTML to {@code report/report.html}.
 *
 * <p>Given: the HTML as a string. Gives back: the path of the file it wrote.
 */
public final class ReportFileWriter {

    /**
     * Saves the HTML as {@code report/report.html}, creating the
     * {@code report} folder if needed. An older report is replaced.
     *
     * @param html the full report page
     * @return the path of the written file, relative to where the app runs
     * @throws IOException if the folder or file cannot be written
     */
    public Path write(String html) throws IOException {
        // Relative path: the folder ends up next to wherever the app is started from.
        Path reportFile = Path.of("report", "report.html");
        Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile, html);
        return reportFile;
    }
}
