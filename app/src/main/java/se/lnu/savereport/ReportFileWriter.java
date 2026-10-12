package se.lnu.savereport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * Writes the finished HTML to {@code report/report.html}.
 * <p>Given: the HTML as a string. Gives back: the path of the file it wrote.
 */
public final class ReportFileWriter {
    public Path write(String html) throws IOException {
        Path reportFile = Path.of("report", "report.html");
        Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile, html);
        return reportFile;
    }
}
