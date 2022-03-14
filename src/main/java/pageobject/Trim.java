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
    private final ITextBox style = getElementFactory().getTextBox(By.xpath(
            "//div[@class='sds-accordion'][1]//th[text()='Style']/../following-sibling::tr[1]/td[1]"),
            "Style");

    private final IButton firstTrim = getElementFactory().getButton(
            By.xpath("(//button[contains(@id,'research-compare-trim')])[1]//span"), "First trim");

    private final Header header = new Header();

    public Trim() {
        super(By.cssSelector("section.trim-accordion-container"), "Trim accordion container");
    }

    public void clickFirstTrim() {
        firstTrim.click();
    }

    public String getStyle() {
        return style.getText();
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

    public void clickResearch() {
        header.clickResearch();
    }
}