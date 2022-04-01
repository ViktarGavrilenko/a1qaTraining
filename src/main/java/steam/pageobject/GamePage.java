package steam.pageobject;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import steam.model.Game;

import static utils.StringUtils.getNumberFromStr;
import static utils.StringUtils.getPriceFromStr;

public class GamePage extends Form {
    private final ITextBox gameDiscount = getElementFactory().getTextBox(
            By.xpath("(//div[@class='game_purchase_action']//div[@class='discount_pct'])[1]"), "Game discount");
    private final ITextBox gameFinalPrice = getElementFactory().getTextBox(By.xpath
            ("(//div[@class='game_purchase_action']//div[@class='discount_final_price'])[1]"), "Game price");
    private final ITextBox gameOriginalPrice = getElementFactory().getTextBox(By.xpath
            ("(//div[@class='game_purchase_action']//div[@class='discount_original_price'])[1]"), "Game price");

    public GlobalHeader globalHeader;

    public GamePage() {
        super(By.cssSelector("#appHubAppName"), "Game Page");
        this.globalHeader = new GlobalHeader();
    }

    public Game getGameFromGamePage() {
        Game game = new Game();
        game.discountPct = getNumberFromStr(gameDiscount.getText());
        game.discountFinalPrice = getPriceFromStr(gameFinalPrice.getText());
        game.discountOriginalPrice = getPriceFromStr(gameOriginalPrice.getText());
        return game;
    }
}