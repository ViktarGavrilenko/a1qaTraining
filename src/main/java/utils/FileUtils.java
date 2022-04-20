package utils;

import aquality.selenium.core.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static void deleteFile(String pathFile) {
        Path pathReport = Paths.get(pathFile);
        try {
            if (Files.exists(pathReport)) {
                Files.delete(pathReport);
            }
        } catch (IOException e) {
            Logger.getInstance().error("File delete error: " + e);
        }
    }
}