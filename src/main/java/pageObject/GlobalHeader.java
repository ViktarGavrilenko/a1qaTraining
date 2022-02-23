package pageObject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class GlobalHeader extends Form {
    private final ILink installSteam = getElementFactory().getLink(
            By.xpath("//a[@class='header_installsteam_btn_content']"), "Install Steam");

    public GlobalHeader() {
        super(By.xpath("//span[@id='logo_holder']"), "Logo holder");
    }

    public void clickInstallSteam() {
        installSteam.click();
    }
}