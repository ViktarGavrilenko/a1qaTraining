import cars.models.Car;
import cars.pageobject.*;
import org.testng.annotations.Test;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static utils.StringUtils.addSpaceBeforeParenthesis;

public class CarsTest extends BaseTest {
    private static final String numberFirstCar = TEST_FILE.getValue("/firstCar").toString();
    private static final String numberSecondCar = TEST_FILE.getValue("/secondCar").toString();
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/carsPage").toString();

    @Test(description = "Cars test")
    public void testCars() {
        getBrowser().goTo(DEFAULT_URL);
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
                descriptionCar.header.clickResearch();
            }
        }
        trim.clickFirstTrim();
        firstCar.style = trim.getStyle();
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
                descriptionCar.header.clickResearch();
            }
        }
        trim.clickFirstTrim();
        secondCar.style = trim.getStyle();
        secondCar.engine = trim.getEngineName();
        secondCar.transmission = trim.getTransmission();

        trim.header.clickResearch();
        research = new Research();
        assertTrue(research.state().isDisplayed(), "Research page not showing");
        research.clickComparisons();

        Compare compare = new Compare();
        assertTrue(compare.state().isDisplayed(), "Compare page not showing");

        compare.clickAddCar();
        compare.addCar(firstCar);
        String detailsFirstCar = firstCar.year + " " + firstCar.make + " " + firstCar.model;
        assertEquals(compare.getDetails(numberFirstCar), detailsFirstCar, "Wrong car selected");

        compare.clickAddCar();
        compare.addCar(secondCar);
        String detailsSecondCar = secondCar.year + " " + secondCar.make + " " + secondCar.model;
        assertEquals(compare.getDetails(numberSecondCar), detailsSecondCar, "Wrong car selected");

        compare.clickSeeComparison();
        assertEquals(compare.getTransmissions(numberFirstCar), firstCar.transmission,
                "The transmission in the first car does not match");
        assertEquals(compare.getTransmissions(numberSecondCar), secondCar.transmission,
                "The transmission in the second car does not match");
        assertEquals(addSpaceBeforeParenthesis(compare.getEngine(numberFirstCar)), firstCar.engine,
                "The engine in the first car does not match");
        assertEquals(addSpaceBeforeParenthesis(compare.getEngine(numberSecondCar)), secondCar.engine,
                "The engine in the second car does not match");
    }
}