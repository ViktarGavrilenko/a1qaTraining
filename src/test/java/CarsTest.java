import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Car;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.*;

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
        Header header = new Header();
        header.clickResearch();

        Research research = new Research();
        assertTrue(research.state().isDisplayed(), "Research page not showing");
        Car firstCar = null;
        Trim trim = null;
        DescriptionCar descriptionCar;
        boolean isTrim = false;
        while (!isTrim) {
            firstCar = research.generateRandomCar();
            descriptionCar = new DescriptionCar();
            assertTrue(descriptionCar.state().isDisplayed(), "Car description page not showing");

            descriptionCar.clickTrim();
            trim = new Trim();
            assertTrue(trim.state().isDisplayed(), "Trim page not showing");

            if (trim.isFirstTrim()) {
                isTrim = true;
            } else {
                descriptionCar.clickResearch();
            }
        }
        trim.clickFirstTrim();
        firstCar.engine = trim.getEngineName();
        firstCar.transmission = trim.getTransmission();

        getBrowser().goTo(DEFAULT_URL);
        header = new Header();
        header.clickResearch();

        research = new Research();
        assertTrue(research.state().isDisplayed(), "Research page not showing");
        Car secondCar = null;
        trim = null;

        isTrim = false;
        while (!isTrim) {
            secondCar = research.generateRandomCar();
            descriptionCar = new DescriptionCar();
            assertTrue(descriptionCar.state().isDisplayed(), "Car description page not showing");

            descriptionCar.clickTrim();
            trim = new Trim();
            assertTrue(trim.state().isDisplayed(), "Trim page not showing");

            if (trim.isFirstTrim()) {
                isTrim = true;
            } else {
                descriptionCar.clickResearch();
            }
        }
        trim.clickFirstTrim();
        secondCar.engine = trim.getEngineName();
        secondCar.transmission = trim.getTransmission();

        trim.clickResearch();
        research = new Research();
        assertTrue(research.state().isDisplayed(), "Research page not showing");
        research.clickComparisons();

        Compare compare = new Compare();
        compare.clickAddCar();
        compare.addFirstCar(firstCar);
        compare.clickAddCar();
        compare.addFirstCar(secondCar);
    }

    @AfterMethod
    public void afterMethod() {
        getBrowser().quit();
    }
}