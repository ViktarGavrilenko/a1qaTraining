package steam.pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import steam.model.Game;

import java.util.List;

import static aquality.selenium.elements.ElementType.TEXTBOX;
import static utils.StringUtils.getNumberFromStr;
import static utils.StringUtils.getPriceFromStr;

public class TabContent extends Form {
    private final List<ITextBox> listDiscountGames = getElementFactory().findElements(By.xpath(
            "//div[@id='tab_content_TopSellers']//div[@class='discount_pct']/.."), "Discount games", TEXTBOX);

    private final By byDiscountPct = By.xpath("//div[@class='discount_pct']");
    private final By byOriginalPrice = By.xpath("//div[@class='discount_original_price']");
    private final By byFinalPrice = By.xpath("//div[@class='discount_final_price']");

    public TabContent() {
        super(By.xpath("//div[@class='mature_content_filtered']"), "Tab content");
    }

    public Game clickGameWithMaxDiscount() {
        int maxNumber = 0;
        int numberOnList = 0;
        Game game = new Game();
        game.discountPct = 0;
        for (ITextBox discountGame : listDiscountGames) {
            ITextBox discountPct = discountGame.findChildElement(byDiscountPct, TEXTBOX);
            if (getNumberFromStr(discountPct.getText()) > game.discountPct) {
                game.discountPct = getNumberFromStr(discountPct.getText());
                ITextBox finalPrice = discountGame.findChildElement(byFinalPrice, TEXTBOX);
                game.discountFinalPrice = getPriceFromStr(finalPrice.getText());
                ITextBox originalPrice = discountGame.findChildElement(byOriginalPrice, TEXTBOX);
                game.discountOriginalPrice = getPriceFromStr(originalPrice.getText());
                maxNumber = numberOnList;
            }
            Logger.getInstance().info("Discount " + discountPct.getText() + " number on list " + numberOnList);
            numberOnList++;
        }
        listDiscountGames.get(maxNumber).click();
        return game;
    }
}