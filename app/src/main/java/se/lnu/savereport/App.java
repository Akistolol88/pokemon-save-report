package se.lnu.savereport;

import se.lnu.savechecker.SaveFile;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        final String testSavePath = "../savechecker/src/test/resources/saves/Yellow/Yellow_Random_01.srm";
        SaveFileLoader loader = new SaveFileLoader();
        SaveFile saveFile = loader.load(testSavePath);

        String html = "<h1>" + saveFile.getTrainer().getName() + "</h1>";
        ReportFileWriter writer = new ReportFileWriter();
        Path reportFile = writer.write(html);

        System.out.println("Report written to " + reportFile);
    }
}
