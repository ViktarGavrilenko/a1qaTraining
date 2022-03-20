package pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import battleship.CellOption;
import org.openqa.selenium.By;

public class MainPage extends Form {
    private final IButton randomly =
            getElementFactory().getButton(By.cssSelector("li[class$='randomly'] span"), "Randomly");

    private final IButton play =
            getElementFactory().getButton(By.cssSelector("div.battlefield-start-button"), "Play");

    private final String cellLocator = "//div[contains(@class,'battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";

    public MainPage() {
        super(By.cssSelector("header h1.logo"), "Name of the game");
    }

    public void clickRandomly() {
        randomly.click();
    }

    public void clickPlay() {
        play.click();
    }

    public CellOption clickCell(int x, int y) {
        IButton cell = getElementFactory().getButton(By.xpath(String.format(cellLocator, x, y)), "Cell");
        Logger.getInstance().error(cell.getAttribute("class"));
        if (cell.getAttribute("class").contains(CellOption.empty.toString())) {
            cell.state().waitForClickable();
            cell.click();
            IButton cellAfterClick = getElementFactory().getButton(By.xpath(String.format(cellLocator, x, y)), "Cell after click");
            return getCellOption(cellAfterClick.getAttribute("class"));

        } else {
            Logger.getInstance().error(cell.getAttribute("class"));
            return getCellOption(cell.getAttribute("class"));        }
    }

    public CellOption getCellOption(String classOfCell){
        if (classOfCell.contains(CellOption.empty.toString())) {
            return CellOption.empty;
        }
        if (classOfCell.contains(CellOption.hit.toString())) {
            return CellOption.hit;
        }
        if (classOfCell.contains(CellOption.miss.toString())) {
            return CellOption.miss;
        }

        return null;
    }
}