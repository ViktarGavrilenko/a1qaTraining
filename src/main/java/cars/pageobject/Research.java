package cars.pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import cars.models.Car;
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
    private final ILink comparisons = getElementFactory().getLink(
            By.xpath("//h3[text()='Side-by-side comparisons']//following-sibling::a"), "Side-by-side comparisons");

    public Research() {
        super(By.cssSelector("div.research-search"), "Research search");
    }

    public String selectRandomMake() {
        make.selectByIndex(generateRandomIntUpToMaxWithoutZero(make.getValues().size()));
        return make.getSelectedText();
    }

    public String selectRandomModel() {
        model.selectByIndex(generateRandomIntUpToMaxWithoutZero(model.getValues().size()));
        return model.getSelectedText();
    }

    public String selectRandomYear() {
        year.selectByIndex(generateRandomIntUpToMaxWithoutZero(year.getValues().size()));
        return year.getSelectedText();
    }

    public void clickResearch() {
        researchButton.click();
    }

    public Car generateRandomCar() {
        Car car = new Car();
        car.make = selectRandomMake();
        car.model = selectRandomModel();
        car.year = selectRandomYear();
        clickResearch();
        return car;
    }

    public void clickComparisons() {
        comparisons.click();
    }
}