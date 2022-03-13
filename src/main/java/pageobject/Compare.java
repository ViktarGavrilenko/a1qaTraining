package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ILink;
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
            getElementFactory().getButton(By.cssSelector("div.sds-field button.sds-button"),"Button add car");

    public Compare() {
        super(By.cssSelector("div.comparison-container"), "Comparison container");
    }

    public void clickAddCar() {
        firstCarAdd.state().waitForClickable();
        firstCarAdd.click();
    }

    public void addFirstCar(Car car){
        make.state().waitForClickable();
        make.selectByText(car.make);
        model.state().waitForClickable();
        model.selectByText(car.model);
        year.state().waitForClickable();
        year.selectByText(car.year);
        trim.state().waitForClickable();
        trim.selectByIndex(1);
        buttonAddCar.click();
    }
}
