package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Trim extends Form {
    private final ITextBox engine = getElementFactory().getTextBox(By.xpath(
            "//div[@class='sds-accordion'][1]//th[text()='Engine']/../following-sibling::tr[1]/td[1]"), "Engine");
    private final ITextBox transmission = getElementFactory().getTextBox(By.xpath(
                    "//div[@class='sds-accordion'][1]//th[text()='Transmission']/../following-sibling::tr[1]/td[1]"),
            "Transmission");
    private final IButton firstTrim =
            getElementFactory().getButton(By.cssSelector("button#research-compare-trim-0-trigger1"), "First trim");

    public Trim() {
        super(By.cssSelector("section.trim-accordion-container"), "Trim accordion container");
    }

    public void clickFirstTrim() {
        firstTrim.click();
    }

    public String getEngineName() {
        return engine.getText();
    }

    public String getTransmission() {
        return transmission.getText();
    }

    public boolean isFirstTrim() {
        return firstTrim.state().isDisplayed();
    }
}