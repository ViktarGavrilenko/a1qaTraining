package pageObject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import model.Game;
import org.openqa.selenium.By;

import java.util.List;

import static Utils.StringUtils.getNumberFromStr;
import static aquality.selenium.elements.ElementType.TEXTBOX;

public class Page extends Form {
    private static final By TITLE_PAGE = By.cssSelector("h2.pageheader");
    private final ITextBox titlePage = getElementFactory().getTextBox(
            TITLE_PAGE, "Title page");

    private final ITextBox tabTopSellers =
            getElementFactory().getTextBox(By.cssSelector("#tab_select_TopSellers"), "TopSellers");

    private final ITextBox tabTopSellersActive =
            getElementFactory().getTextBox(By.cssSelector("#tab_select_TopSellers.active"), "TopSellers");

    private final By selectorDiscountPct = By.cssSelector("div#tab_content_TopSellers .discount_pct");

    //div[@id='tab_content_TopSellers']//div[@class='discount_pct']//following::div[@class='discount_final_price'][1]


    public Page() {
        super(TITLE_PAGE, "Title page");
    }

    public boolean isPageDisplayed(String namePage) {
        return titlePage.getText().equals(namePage);
    }

    public void clickTabTopSeller() {
        tabTopSellers.click();
    }

    public boolean isActiveTabTopSeller() {
        return tabTopSellersActive.state().isDisplayed();
    }

    public Game clickGameWithMaxDiscount() {
        List<ITextBox> listDiscountPct =
                getElementFactory().findElements(selectorDiscountPct, "DiscountPct", TEXTBOX);
        int maxDiscount = 0;
        int maxNumber = 0;
        int numberOnList = 0;
        int gamePrice = 0;
        Game game = new Game();
        for (ITextBox discountPct : listDiscountPct) {
            if (getNumberFromStr(discountPct.getText()) > maxDiscount) {
                maxDiscount = getNumberFromStr(discountPct.getText());
                gamePrice =
                        maxNumber = numberOnList;
            }
            Logger.getInstance().info("Discount " + discountPct.getText() + " number on list " + numberOnList);
            numberOnList++;
        }
        listDiscountPct.get(maxNumber).click();
    }
}
