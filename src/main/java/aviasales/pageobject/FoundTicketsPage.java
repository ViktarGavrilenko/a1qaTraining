package aviasales.pageobject;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FoundTicketsPage extends Form {
    private final ITextBox routeDuration =
            getElementFactory().getTextBox(By.cssSelector("div.segment-route__duration"), "Route duration");

    public FoundTicketsPage() {
        super(By.cssSelector("div.app__container"), "Container");
    }

}
