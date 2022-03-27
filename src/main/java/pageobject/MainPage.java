package pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import battleship.Cell;
import battleship.CellOption;
import org.openqa.selenium.By;

import java.time.Duration;

public class MainPage extends Form {
    private final IButton randomly =
            getElementFactory().getButton(By.cssSelector("li[class$='randomly'] span"), "Randomly");

    private final IButton play =
            getElementFactory().getButton(By.cssSelector("div.battlefield-start-button"), "Play");

    private final String cellLocator =
            "//div[contains(@class,'battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";

    private final ITextBox battlefieldOfRival = getElementFactory().getTextBox(
            By.xpath("//div[@class='battlefield battlefield__rival']"), "Battlefield of rival");

    private final String statusGame = "//div[contains(@class,'%s') and not(contains(@class,'none'))]";

/*     private final ITextBox gameLose = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'game-over-lose') and not(contains(@class,'none'))]"),
                    "Game is lose");
    private final ITextBox gameWin = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'game-over-win') and not(contains(@class,'none'))]"),
                    "Game is win");
    private final ITextBox rivalLeave = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'rival-leave') and not(contains(@class,'none'))]"),
                    "Rival is gone");
    private final ITextBox serverError = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'server-error') and not(contains(@class,'none'))]"),
                    "Server is not available");
    private final ITextBox gameError = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'game-error') and not(contains(@class,'none'))]"),
                    "Unexpected error");

    private final ITextBox onMove = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'move-on') and not(contains(@class,'none'))]"),
                    "Move on");
    private final ITextBox offMove = getElementFactory().getTextBox
            (By.xpath("//div[contains(@class,'move-off') and not(contains(@class,'none'))]"),
                    "Move off");*/


    public MainPage() {
        super(By.cssSelector("header h1.logo"), "Name of the game");
    }

    public void clickRandomly() {
        randomly.click();
    }

    public void clickPlay() {
        play.click();
    }

    public boolean isBattlefieldOfRivalClick() {
        return battlefieldOfRival.state().waitForDisplayed(Duration.ofSeconds(30));
    }

    public Cell clickCell(Cell cellClick) {
        IButton cell = getElementFactory().getButton(
                By.xpath(String.format(cellLocator, cellClick.y, cellClick.x)), "Cell");
        if (cell.getAttribute("class").contains(CellOption.empty.toString())) {
            cell.state().waitForClickable();
            cell.clickAndWait();
            IButton cellAfterClick = getElementFactory().getButton(
                    By.xpath(String.format(cellLocator, cellClick.y, cellClick.x)), "Cell after click");
            return getCellOption(cellClick, cellAfterClick.getAttribute("class"));

        } else {
            Logger.getInstance().error(cell.getAttribute("class"));
            return getCellOption(cellClick, cell.getAttribute("class"));
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

    public boolean getStatusGame() {
        ITextBox status = getElementFactory().getTextBox
                (By.xpath(String.format(statusGame, Notification.MOVE_ON)), "Game status");
        status.state().waitForDisplayed(Duration.ofSeconds(1));

        for (Notification notification : Notification.values()) {
            Logger.getInstance().info(notification.getTextNotification());

            ITextBox statuses = getElementFactory().getTextBox
                    (By.xpath(String.format(statusGame, notification.getTextNotification())), "Game status");

            if (statuses.state().isDisplayed()) {
                Logger.getInstance().info("--------Game status is " + notification.getTextNotification());

            }
        }
        return false;
    }
}