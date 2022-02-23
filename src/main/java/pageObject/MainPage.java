package pageObject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {
    private final ILink linkCategories = getElementFactory().getLink(
            By.cssSelector("#genre_tab a.pulldown_desktop"), "Categories");
    private final ILink linkActions = getElementFactory().getLink(
            By.cssSelector("div.popup_menu_subheader[data-genre-group='action'] a.popup_menu_item"), "Action");

    public MainPage() {
        super(By.cssSelector("div.home_cluster_ctn h2.home_page_content_title"), "HomePageTitle");
    }

    public void clickLinkCategories() {
        linkCategories.click();
    }

    public void clickLinkActions() {
        linkActions.click();
    }
}