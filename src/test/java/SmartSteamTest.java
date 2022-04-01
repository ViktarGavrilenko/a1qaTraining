import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steam.model.Game;
import steam.pageobject.*;

import java.io.File;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.BrowserUtils.getFileLength;
import static utils.BrowserUtils.isDownloadFile;
import static utils.StringUtils.getNameFileFromUrl;

public class SmartSteamTest {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("configData.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/steamPage").toString();
    private static final int YEAR_OLD = (int) TEST_DATA_FILE.getValue("/yearOld");
    private static final String DOWNLOADS_DIR = TEST_DATA_FILE.getValue("/downloadsDir").toString();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @Test(description = "Test SmartSteam")
    public void testSmartSteam() {
        SteamPage steamPage = new SteamPage();
        assertTrue(steamPage.state().isDisplayed(), "Main page not showing");
        steamPage.clickLinkCategories();
        steamPage.clickLinkActions();
        ActionSteamPage actionSteamPage = new ActionSteamPage();
        assertTrue(actionSteamPage.state().isDisplayed(), "ActionSteam page not showing");

        actionSteamPage.clickTabTopSeller();
        assertTrue(actionSteamPage.isActiveTabTopSeller(), "TopSeller tab is not active");

        actionSteamPage.createTabContent();
        Game gameFromTopSeller = actionSteamPage.tabContent.clickGameWithMaxDiscount();
        AgeCheckFirstPage ageCheck = new AgeCheckFirstPage();
        AgeCheckSecondPage ageCheckSecond = new AgeCheckSecondPage();

        if (ageCheck.state().isDisplayed()) {
            ageCheck.voiceDate(YEAR_OLD);
        } else {
            if (ageCheckSecond.state().isDisplayed()) {
                ageCheckSecond.clickButtonViewPage();
            }
        }

        GamePage gamePage = new GamePage();
        Game gameFromGamePage = gamePage.getGameFromGamePage();
        assertEquals(gameFromGamePage, gameFromTopSeller, "Game price or discount does not match");

        gamePage.globalHeader.clickInstallSteam();
        AboutPage aboutPage = new AboutPage();
        aboutPage.clickInstallSteam();
        long fileSizeOnWebSite = getFileLength(aboutPage.getUrlFromLink());
        String filePath = DOWNLOADS_DIR + "/" + getNameFileFromUrl(aboutPage.getUrlFromLink());
        assertTrue(isDownloadFile(filePath), "File should be downloaded");

        File file = new File(filePath);
        assertEquals(file.length(), fileSizeOnWebSite, "File sizes are not equal");
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}