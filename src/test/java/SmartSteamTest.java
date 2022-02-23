import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import model.Game;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.*;

import static Utils.BrowserUtils.clickEnter;
import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SmartSteamTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("configData.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static final int YEAR_OLD = (int) TEST_DATA_FILE.getValue("/yearOld");

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @Test(description = "Test of accept cookies ")
    public void testCookies() {
        MainPage mainPage = new MainPage();
        assertTrue(mainPage.state().isDisplayed(), "Main page not showing");
        mainPage.clickLinkCategories();
        mainPage.clickLinkActions();

        Page page = new Page();
        assertTrue(page.state().isDisplayed(), "Page not showing");

        page.clickTabTopSeller();
        assertTrue(page.isActiveTabTopSeller(), "TopSeller tab is not active");
        page.createTabContent();
        Game gameFromTopSeller = page.tabContent.clickGameWithMaxDiscount();

        AgeCheckPage ageCheck = new AgeCheckPage();
        if (ageCheck.state().isDisplayed()) {
            ageCheck.voiceDay();
            ageCheck.voiceMonth();
            ageCheck.voiceYear(YEAR_OLD);
            ageCheck.clickViewPage();
        }

        GamePage gamePage = new GamePage();
        Game gameFromGamePage = gamePage.getGameFromGamePage();

        assertEquals(gameFromGamePage, gameFromTopSeller, "Game price or discount does not match");

        gamePage.globalHeader.clickInstallSteam();

        AboutPage aboutPage = new AboutPage();
        aboutPage.clickInstallSteam();
        clickEnter();
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
