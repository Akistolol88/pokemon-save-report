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

    /** Reads what the user types. Created once and reused for every question. */
    private final Scanner keyboard = new Scanner(System.in);

    /**
     * Prints what the app does and how to use it: type a save file path to get
     * a report, or {@code quit} to close the app (R1).
     */
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

    /**
     * Asks for the path to a save file and waits until the user presses Enter.
     *
     * @return exactly what the user typed, which can also be {@code quit}
     */
    public String askForSavePath() {
        System.out.print("Save file path (or quit): ");
        return keyboard.nextLine();
    }

    /**
     * Tells the user where the finished report was saved (R2).
     *
     * @param reportFile the path of the report file that was written
     */
    public void showReportWritten(Path reportFile) {
        System.out.println("Report written to " + reportFile);
    }

    /**
     * Tells the user that something went wrong, so they can try again
     * (R12, R13).
     *
     * @param message what went wrong, in words the user understands
     */
    public void showError(String message) {
        System.out.println(message);
    }
}
