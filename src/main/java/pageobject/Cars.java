package pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Cars extends Form {
    private final ILink research = getElementFactory().
            getLink(By.cssSelector("li.header-link a[data-linkname$='research']"), "Research");

    public Cars() {
        super(By.cssSelector("div#search-widget"), "Search widget");
    }

    public void clickResearch() {
        research.click();
    }
}