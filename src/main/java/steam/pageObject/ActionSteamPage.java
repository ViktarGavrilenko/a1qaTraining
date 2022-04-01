package steam.pageObject;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ActionSteamPage extends Form {
    private static final By TITLE_PAGE = By.cssSelector("h2.pageheader");

    private final ITextBox tabTopSellers =
            getElementFactory().getTextBox(By.cssSelector("#tab_select_TopSellers"), "TopSellers");
    private final ITextBox tabTopSellersActive =
            getElementFactory().getTextBox(By.cssSelector("#tab_select_TopSellers.active"), "TopSellers");

    public TabContent tabContent;

    public ActionSteamPage() {
        super(TITLE_PAGE, "Title page");
    }

    public void clickTabTopSeller() {
        tabTopSellers.click();
    }

    public boolean isActiveTabTopSeller() {
        return tabTopSellersActive.state().isDisplayed();
    }

    public void createTabContent() {
        tabContent = new TabContent();
    }
}