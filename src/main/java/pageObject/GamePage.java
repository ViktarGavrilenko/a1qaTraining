package pageObject;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class GamePage extends Form {
    private final ITextBox gameDiscount = getElementFactory().getTextBox(
            By.xpath("(//div[@class='game_purchase_action']//div[@class='discount_pct'])[1]"), "Game discount");

    private final ITextBox gamePrice = getElementFactory().getTextBox(By.xpath
            ("(//div[@class='game_purchase_action']//div[@class='discount_final_price'])[1]"), "Game price");

    public GamePage() {
        super(By.cssSelector("#appHubAppName"), "Game Page");
    }

    public String getGameDiscount() {
        return gameDiscount.getText();
    }

    public String getGamePrice() {
        return gamePrice.getText();
    }
}
