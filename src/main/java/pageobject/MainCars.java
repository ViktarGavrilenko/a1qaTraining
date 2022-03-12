package pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainCars extends Form {
    private final ILink research = getElementFactory().
            getLink(By.cssSelector("li.header-link a[data-linkname$='research']"), "Research");

    public MainCars() {
        super(By.cssSelector("header.global-header"), "Header");
    }

    public void clickResearch() {
        research.click();
    }
}