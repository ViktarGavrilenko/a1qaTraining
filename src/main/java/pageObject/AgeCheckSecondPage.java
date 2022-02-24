package pageObject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AgeCheckSecondPage extends Form {
    private ILink linkViewPage = getElementFactory().getLink(
            By.xpath("//div[@id='app_agegate']//a[@href='#']"), "Button View Page");

    public AgeCheckSecondPage() {
        super(By.xpath("//div[@class='agegate_text_container']//div[@class='img_ctn']"), "AgeCheckSecondPage");
    }

    public void clickButtonViewPage() {
        linkViewPage.click();
    }
}