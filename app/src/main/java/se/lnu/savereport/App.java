package se.lnu.savereport;

import se.lnu.savechecker.SaveFile;

public class App {
    public static void main(String[] args) throws Exception {
        final String testSavePath = "../savechecker/src/test/resources/saves/Yellow/Yellow_Random_01.srm";
        SaveFileLoader loader = new SaveFileLoader();
        SaveFile saveFile = loader.load(testSavePath);

        System.out.println(saveFile.getTrainer().getName());
    }
}
