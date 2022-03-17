package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {
    private final IButton randomly =
            getElementFactory().getButton(By.cssSelector("li[class$='randomly'] span"), "Randomly");

    private final IButton play =
            getElementFactory().getButton(By.cssSelector("div.battlefield-start-button"), "Play");

    private final IButton emptyCell =
            getElementFactory().getButton(By.cssSelector("div.battlefield__rival td.battlefield-cell__empty"), "Empty cell");


    public MainPage() {
        super(By.cssSelector("header h1.logo"), "Name of the game");
    }

    public void clickRandomly() {
        randomly.click();
    }

    public void clickPlay() {
        play.click();
    }

    public void clickEmptyCell() {
        emptyCell.click();
    }

    public boolean isEmptyCell() {
        return emptyCell.state().isClickable();
    }
}