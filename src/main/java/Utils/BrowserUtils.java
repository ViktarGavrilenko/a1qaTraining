package Utils;

import aquality.selenium.core.logging.Logger;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BrowserUtils {
    public static void clickEnter() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Logger.getInstance().error("InterruptedException: " + e);
        }

        try {
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            Logger.getInstance().error("AWTException: " + e);
        }
    }
}
