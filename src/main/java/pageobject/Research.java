package pageobject;

import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Research extends Form {
    private final IComboBox make =
            getElementFactory().getComboBox(By.cssSelector("select#make-select"), "Car make");

    private final IComboBox model =
            getElementFactory().getComboBox(By.cssSelector("select#model-select"), "Car model");

    private final IComboBox year =
            getElementFactory().getComboBox(By.cssSelector("select#year-select"), "Car year");

    public Research() {
        super(By.cssSelector("div.research-search"), "Research search");
    }

}
