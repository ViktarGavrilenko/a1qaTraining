package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import static utils.ArithmeticUtils.generateRandomIntUpToMaxWithoutZero;

public class Research extends Form {
    private final IComboBox make =
            getElementFactory().getComboBox(By.cssSelector("select#make-select"), "Car make");
    private final IComboBox model =
            getElementFactory().getComboBox(By.cssSelector("select#model-select"), "Car model");
    private final IComboBox year =
            getElementFactory().getComboBox(By.cssSelector("select#year-select"), "Car year");
    private final IButton researchButton =
            getElementFactory().getButton(By.cssSelector("div.research-search button"), "Research button");

    public Research() {
        super(By.cssSelector("div.research-search"), "Research search");
    }

    public void selectRandomMake() {
        make.selectByIndex(generateRandomIntUpToMaxWithoutZero(make.getValues().size()));
    }

    public void selectRandomModel() {
        model.selectByIndex(generateRandomIntUpToMaxWithoutZero(model.getValues().size()));
    }

    public void selectRandomYear() {
        year.selectByIndex(generateRandomIntUpToMaxWithoutZero(year.getValues().size()));
    }

    public void clickResearch() {
        researchButton.click();
    }

    public void generateRandomCar() {
        selectRandomMake();
        selectRandomModel();
        selectRandomYear();
        clickResearch();
    }
}