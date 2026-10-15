package se.lnu.savereport;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Talks to the user in the terminal: shows the welcome message, asks for the
 * path to a save file, reads what the user types and prints messages.
 *
 * <p>This is the only class that reads from the keyboard or prints to the
 * screen.
 */
public final class ConsoleUi {
    private final Scanner keyboard = new Scanner(System.in);

    public void showWelcomeMessage() {
        System.out.println("=== Pokémon Save Report ===");
        System.out.println("Turns a Pokémon Red, Blue or Yellow save file (.sav or .srm) into a");
        System.out.println("report with the trainer's badges, Pokédex and party, plus charts of");
        System.out.println("the party's levels and EVs.");
        System.out.println();
        System.out.println("How to use it:");
        System.out.println("  - Type the path to a save file and press Enter.");
        System.out.println("    The report is saved as report/report.html. Open it in a browser.");
        System.out.println("  - After each report you can enter another save file.");
        System.out.println("  - Type quit and press Enter to close the app.");
        System.out.println();
    }

    public String askForSavePath() {
        System.out.print("Save file path (or quit): ");
        return keyboard.nextLine();
    }

    public void showReportWritten(Path reportFile) {
        System.out.println("Report written to " + reportFile);
    }
}
