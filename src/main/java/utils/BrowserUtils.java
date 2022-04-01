package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class BrowserUtils {
    public static boolean isDownloadFile(String file) {
        Path p = Paths.get(file);
        try {
            AqualityServices.getConditionalWait().waitForTrue(() -> Files.exists(p), "File should be downloaded");
            return true;
        } catch (TimeoutException e) {
            Logger.getInstance().error("TimeoutException: " + e);
            return false;
        }
    }

    public static long getFileLength(String downloadUrl) {
        if (downloadUrl == null || "".equals(downloadUrl)) {
            return 0L;
        }
        HttpURLConnection conn = null;
        try {
            URL url = new URL(downloadUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows 7; WOW64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 YNoteCef/5.8.0.1 (Windows)");
            return conn.getContentLength();
        } catch (IOException e) {
            return 0L;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}