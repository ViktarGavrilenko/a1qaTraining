package aviasales.pageobject;

import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class TicketSearchPage extends Form {
    private final ITextBox origin =
            getElementFactory().getTextBox(By.cssSelector("input#origin"), "Origin");
    private final ITextBox destination =
            getElementFactory().getTextBox(By.cssSelector("input#destination"), "Destination");
    private final ITextBox departureDate = getElementFactory().getTextBox(
            By.xpath("//input[@data-test-id='departure-date-input']//.."), "Departure date");
    private final ITextBox returnDate = getElementFactory().getTextBox(
            By.xpath("//input[@data-test-id='return-date-input']//.."), "Return date");
    private final String calendarDayLocator = "//div[contains(@aria-label,'%s')]";
    private final ICheckBox passengers = getElementFactory().getCheckBox(
            By.xpath("//div[contains(@class,'passengers')]"), "Passengers");
    private final ICheckBox tripClassEconomy = getElementFactory().getCheckBox(
            By.xpath("//label[contains(@class,'economy')]"), "Trip class Economy");
    private final ITextBox submit =
            getElementFactory().getTextBox(By.cssSelector("div.avia-form__submit"), "Submit");

    public TicketSearchPage() {
        super(By.cssSelector("form.avia-form, name"), "Avia form");
    }

    public void enterOriginCity(String cityName) {
        origin.type(cityName);
    }

    public void enterDestinationCity(String cityName) {
        destination.type(cityName);
    }

    public void enterDepartureDate(String day) {
        departureDate.click();
        ITextBox departureCalendarDay = getElementFactory().getTextBox(
                By.xpath(String.format(calendarDayLocator, day)), "Departure calendar day");
        departureCalendarDay.click();
    }

    public void enterReturnDate(String day) {
        returnDate.click();
        ITextBox returnCalendarDay = getElementFactory().getTextBox(
                By.xpath(String.format(calendarDayLocator, day)), "Return calendar day");
        returnCalendarDay.click();
    }

    public void entryTripClassEconomy() {
        passengers.click();
        tripClassEconomy.click();
    }

    public void clickSubmit() {
        submit.click();
    }
}