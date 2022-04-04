import org.testng.annotations.Test;
import steam.model.Game;
import steam.pageobject.*;

import java.io.File;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static utils.BrowserUtils.getFileLength;
import static utils.BrowserUtils.isDownloadFile;
import static utils.StringUtils.getNameFileFromUrl;

public class SmartSteamTest extends BaseTest {
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/steamPage").toString();
    private static final int YEAR_OLD = (int) TEST_FILE.getValue("/yearOld");
    private static final String DOWNLOADS_DIR = TEST_FILE.getValue("/downloadsDir").toString();

    @Test(description = "Test SmartSteam")
    public void testSmartSteam() {
        getBrowser().goTo(DEFAULT_URL);
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
}