package steam.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AboutPage extends Form {
    private final ILink linkInstallSteam = getElementFactory().getLink(
            By.xpath("//div[@id='about_greeting']//a[@class='about_install_steam_link']"), "Link install Steam");

    public AboutPage() {
        super(By.xpath("//div[@id='about_greeting']"), "About Page");
    }

    public void clickInstallSteam() {
        linkInstallSteam.click();
    }

    public String getUrlFromLink() {
        return linkInstallSteam.getHref();
    }
}