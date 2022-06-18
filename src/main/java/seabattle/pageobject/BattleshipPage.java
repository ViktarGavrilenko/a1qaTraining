package seabattle.pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import seabattle.battleship.Cell;
import seabattle.battleship.CellOption;

import java.time.Duration;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BattleshipPage extends Form {
    private final IButton randomly =
            getElementFactory().getButton(By.cssSelector("li[class$='randomly'] span"), "Randomly");

    private final IButton play =
            getElementFactory().getButton(By.cssSelector("div.battlefield-start-button"), "Play");

    private final String cellLocator =
            "//div[contains(@class,'battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";

    private final ITextBox battlefieldOfRival = getElementFactory().getTextBox(
            By.xpath("//div[@class='battlefield battlefield__rival']"), "Battlefield of rival");

    private final String statusGame = "//div[contains(@class,'%s') and not(contains(@class,'none'))]";

    public BattleshipPage() {
        super(By.cssSelector("header h1.logo"), "Name of the game");
    }

    public void clickRandomly() {
        Actions action = new Actions(getBrowser().getDriver());
        action.moveToElement(randomly.getElement()).click().perform();
    }

    public void clickPlay() {
        play.click();
    }

    public boolean isBattlefieldOfRivalClick() {
        return battlefieldOfRival.state().waitForDisplayed(Duration.ofSeconds(30));
    }

    public Cell clickCell(Cell cellClick) {
        String classAttribute = "class";
        IButton cell = getElementFactory().getButton(
                By.xpath(String.format(cellLocator, cellClick.y, cellClick.x)), "Cell");
        if (cell.getAttribute(classAttribute).contains(CellOption.empty.toString())) {
            cell.state().waitForClickable();
            cell.clickAndWait();
            IButton cellAfterClick = getElementFactory().getButton(
                    By.xpath(String.format(cellLocator, cellClick.y, cellClick.x)), "Cell after click");
            return getCellOption(cellClick, cellAfterClick.getAttribute(classAttribute));

        } else {
            Logger.getInstance().error(cell.getAttribute(classAttribute));
            return getCellOption(cellClick, cell.getAttribute(classAttribute));
        }
    }

    public Cell getCellOption(Cell cellClick, String classOfCell) {
        if (classOfCell.contains(CellOption.empty.toString())) {
            cellClick.option = CellOption.empty;
        }
        if (classOfCell.contains(CellOption.hit.toString())) {
            cellClick.option = CellOption.hit;
        }
        if (classOfCell.contains(CellOption.miss.toString())) {
            cellClick.option = CellOption.miss;
        }
        return cellClick;
    }

    public String getLastGameStatus() {
        String finalMessage = "Unknown error";
        for (NotificationBattleship notificationBattleship : NotificationBattleship.values()) {
            ITextBox statuses = getElementFactory().getTextBox
                    (By.xpath(String.format(statusGame, notificationBattleship.getTextNotification())), "Game status");

            if (statuses.state().isDisplayed()) {
                switch (notificationBattleship) {
                    case LOSE:
                        finalMessage = "You LOSE";
                        break;
                    case WIN:
                        finalMessage = "You WIN";
                        break;
                    case GAME_ERROR:
                        finalMessage = "GAME ERROR";
                        break;
                    case RIVAL_LEAVE:
                        finalMessage = "Opponent left the game";
                        break;
                    case MOVE_OFF:
                        finalMessage = "Opponent takes a long time to move";
                        break;
                    case MOVE_ON:
                        finalMessage = "You take a long time to make a move";
                        break;
                    case SERVER_ERROR:
                        finalMessage = "SERVER ERROR";
                        break;
                }
            }
        }
        Logger.getInstance().info("Game ended: '" + finalMessage);
        return finalMessage;
    }

    public boolean isStatusGame(String waitStatus) {
        ITextBox status = getElementFactory().getTextBox
                (By.xpath(String.format(statusGame, waitStatus)), "Game status");
        return status.state().waitForDisplayed();
    }
}