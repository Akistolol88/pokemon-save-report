package se.lnu.savereport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import se.lnu.savechecker.InvalidSaveFileException;
import se.lnu.savechecker.SaveFile;

/**
 * Reads a save file from disk and turns it into a
 * {@link se.lnu.savechecker.SaveFile}.
 *
 * <p>Given: the path the user typed. Gives back: the loaded save file.
 */
public final class SaveFileLoader {
    public SaveFile load(String savefile) throws IOException, InvalidSaveFileException {
        byte[] bytes = Files.readAllBytes(Path.of(savefile));
        SaveFile saveFile = new SaveFile(bytes);
        return saveFile;
    }
}
