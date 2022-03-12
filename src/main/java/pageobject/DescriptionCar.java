package pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class DescriptionCar extends Form {
    private final ILink trim =
            getElementFactory().getLink(By.cssSelector("div.page-section__content p a"), "Trim link");

    public DescriptionCar() {
        super(By.cssSelector("div.research-mmy-page"), "Description car");
    }

    public void clickTrim() {
        trim.click();
    }
}
