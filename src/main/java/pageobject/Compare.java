package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import models.Car;
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
    private final ITextBox detailsFirstCar =
            getElementFactory().getTextBox(By.xpath("(//div[@class='details'])[1]//p[1]"), "Details first car");
    private final ITextBox detailsSecondCar =
            getElementFactory().getTextBox(By.xpath("(//div[@class='details'])[2]//p[1]"), "Details second car");
    private final IButton seeComparison = getElementFactory().
            getButton(By.cssSelector("div.comparison-container button.sds-button"), "See the comparison");
    private final ITextBox engineFirst = getElementFactory().getTextBox(By.xpath(
            "((//tr[@class='table-section engine-section']//following-sibling::tr)[1]//p)[1]"), "Engine of first car");
    private final ITextBox engineSecond = getElementFactory().getTextBox(By.xpath(
            "((//tr[@class='table-section engine-section']//following-sibling::tr)[1]//p)[2]"), "Engine of second car");
    private final ITextBox transmissionsFirst = getElementFactory().getTextBox(By.xpath(
            "//td[text()='Transmissions']//..//following-sibling::tr[1]//td[1]//p[1]"), "Transmissions of first car");
    private final ITextBox transmissionsSecond = getElementFactory().getTextBox(By.xpath(
            "//td[text()='Transmissions']//..//following-sibling::tr[1]//td[1]//p[1]"), "Transmissions of second car");

    public Compare() {
        super(By.cssSelector("div.comparison-container"), "Comparison container");
    }

    public void clickAddCar() {
        firstCarAdd.state().waitForClickable();
        firstCarAdd.click();
    }

    public void addCar(Car car) {
        make.state().waitForClickable();
        make.selectByText(car.make);
        model.state().waitForClickable();
        model.selectByText(car.model);
        year.state().waitForClickable();
        year.selectByText(car.year);
        trim.state().waitForClickable();
        trim.selectByText(car.style);
        buttonAddCar.click();
    }

    public String getDetailsFirstCar() {
        return detailsFirstCar.getText();
    }

    public String getDetailsSecondCar() {
        return detailsSecondCar.getText();
    }

    public void clickSeeComparison() {
        seeComparison.click();
    }

    public String getFirstEngine() {
        return engineFirst.getText();
    }

    public String getSecondEngine() {
        return engineSecond.getText();
    }

    public String getFirstTransmissions() {
        return transmissionsFirst.getText();
    }

    public String getSecondTransmissions() {
        return transmissionsSecond.getText();
    }
}
