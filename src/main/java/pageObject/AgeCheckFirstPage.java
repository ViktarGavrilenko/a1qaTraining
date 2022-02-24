package pageObject;

import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.Random;

public class AgeCheckFirstPage extends Form {
    private final IComboBox ageDay = getElementFactory().getComboBox(By.cssSelector("#ageDay"), "Age Day");

    private final IComboBox ageMonth = getElementFactory().getComboBox(By.cssSelector("#ageMonth"), "Age Month");
    private final IComboBox ageYear = getElementFactory().getComboBox(By.cssSelector("#ageYear"), "Age Year");
    private final ILink linkViewPage =
            getElementFactory().getLink(By.cssSelector("#view_product_page_btn"), "Link View Page");

    public AgeCheckFirstPage() {
        super(By.cssSelector(".agegate_birthday_desc"), "AgeCheck Page");
    }

    public void voiceDay() {
        ageDay.selectByIndex(new Random().nextInt((ageDay.getValues().size())));
    }

    public void voiceMonth() {
        ageMonth.selectByIndex(new Random().nextInt((ageMonth.getValues().size())));
    }

    public void voiceYear(int year) {
        ageYear.selectByIndex(ageYear.getValues().size() - year);
    }

    public void clickViewPage() {
        linkViewPage.click();
    }

    public void voiceDate(int year){
        voiceDay();
        voiceMonth();
        voiceYear(year);
        clickViewPage();
    }
}