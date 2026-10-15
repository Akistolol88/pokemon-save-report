package se.lnu.savereport;

import se.lnu.savechecker.SaveFile;
import java.nio.file.Path;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ConsoleUi consoleUi = new ConsoleUi();
        SaveFileLoader loader = new SaveFileLoader();
        ChartBuilder chartBuilder = new ChartBuilder();
        HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
        ReportFileWriter writer = new ReportFileWriter();

        consoleUi.showWelcomeMessage();

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
