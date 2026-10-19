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

    /**
     * Reads the file at the given path and checks that it is a real save file.
     *
     * @param path the path to the save file, as the user typed it
     * @return the loaded save file
     * @throws IOException if the file does not exist or cannot be read
     * @throws InvalidSaveFileException if the file is not a valid Pokémon Red,
     *     Blue or Yellow save
     */
    public SaveFile load(String path) throws IOException, InvalidSaveFileException {
        byte[] bytes = Files.readAllBytes(Path.of(path));
        return new SaveFile(bytes);
    }
}
