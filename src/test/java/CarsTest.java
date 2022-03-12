import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.Cars;
import pageobject.DescriptionCar;
import pageobject.Research;
import pageobject.Trim;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class CarsTest extends Assert {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("configData.json");
    private static final ISettingsFile TEST_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().maximize();
        getBrowser().goTo(DEFAULT_URL);
    }

    @Test(description = "Cars test")
    public void testCars() {
        Cars cars = new Cars();
        cars.clickResearch();
        Research research = new Research();
        assertTrue(research.state().isDisplayed(), "Research page not showing");
        research.generateRandomCar();
        DescriptionCar descriptionCar = new DescriptionCar();
        assertTrue(descriptionCar.state().isDisplayed(), "Car description page not showing");
        descriptionCar.clickTrim();
        Trim trim = new Trim();
        assertTrue(trim.state().isDisplayed(), "Trim page not showing");
    }

    @AfterMethod
    public void afterMethod() {
        getBrowser().quit();
    }
}