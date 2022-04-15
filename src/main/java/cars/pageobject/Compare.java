package cars.pageobject;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import cars.models.Car;
import org.openqa.selenium.By;

public class Compare extends Form {
    private final ILink firstCarAdd =
            getElementFactory().getLink(By.xpath("//div//a[@class='add-car']"), "Add car");
    private final IComboBox make =
            getElementFactory().getComboBox(By.cssSelector("select#vehicle_selection_make"), "Car make");
    private final IComboBox model =
            getElementFactory().getComboBox(By.cssSelector("select#vehicle_selection_model"), "Car model");
    private final IComboBox year =
            getElementFactory().getComboBox(By.cssSelector("select#vehicle_selection_year"), "Car year");
    private final IComboBox trim =
            getElementFactory().getComboBox(By.cssSelector("select#vehicle_selection_trim"), "Car trim");
    private final IButton buttonAddCar =
            getElementFactory().getButton(By.cssSelector("div.sds-field button.sds-button"), "Button add car");
    private final IButton seeComparison = getElementFactory().
            getButton(By.cssSelector("div.comparison-container button.sds-button"), "See the comparison");

    private final String details = "(//div[@class='details'])[%s]//p[1]";
    private final String engine = "((//tr[@class='table-section engine-section']//following-sibling::tr)[1]//td)[%s]";
    private final String transmissions = "//td[text()='Transmissions']//..//following-sibling::tr[1]//td[%s]//p[1]";

    public Compare() {
        super(By.cssSelector("div.comparison-container"), "Comparison container");
    }

    public void clickAddCar() {
        firstCarAdd.click();
        if (!make.state().waitForDisplayed()) {
            clickAddCar();
        }
    }

    public void addCar(Car car) {
        make.state().waitForClickable();
        make.state().waitForClickable();
        make.selectByText(car.make);
        model.state().waitForClickable();
        model.selectByText(car.model);
        year.state().waitForClickable();
        year.selectByText(car.year);
        trim.state().waitForClickable();
        trim.selectByText(car.style);
        buttonAddCar.state().waitForClickable();
        buttonAddCar.click();
    }

    public void clickSeeComparison() {
        seeComparison.click();
    }

    public String getDetails(String numberCar) {
        ITextBox detailsOfCar = getElementFactory().getTextBox(By.xpath(String.format(details, numberCar)),
                "Details " + numberCar + " car");
        return detailsOfCar.getText();
    }

    public String getEngine(String numberCar) {
        ITextBox engineOfCar = getElementFactory().getTextBox(By.xpath(String.format(engine, numberCar)),
                "Engine of " + numberCar + " car");
        if (engineOfCar.findChildElement(By.cssSelector("p"), ElementType.TEXTBOX).state().isDisplayed()) {
            return engineOfCar.findChildElement(By.cssSelector("p"), ElementType.TEXTBOX).getText();
        } else {
            return engineOfCar.getText();
        }
    }

    public String getTransmissions(String numberCar) {
        ITextBox transmissionsOfCar = getElementFactory().getTextBox(By.xpath(String.format(transmissions, numberCar)),
                "Transmissions of " + numberCar + " car");
        return transmissionsOfCar.getText();
    }
}