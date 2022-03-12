package pageobject;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Trim extends Form {

    public Trim() {
        super(By.cssSelector("section.trim-accordion-container"), "Trim accordion container");
    }
}