import aviasales.pageobject.FoundTicketsPage;
import aviasales.pageobject.TicketSearchPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class AviaSalesTest extends BaseTest {
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/aviaSalesPage").toString();
    private static final String ORIGIN_CITY = TEST_FILE.getValue("/originCity").toString();
    private static final String DESTINATION_CITY = TEST_FILE.getValue("/destinationCity").toString();
    private static final String DEPARTURE_DAY = TEST_FILE.getValue("/departureDay").toString();
    private static final String RETURN_DAY = TEST_FILE.getValue("/returnDay").toString();

    @Test(description = "AviaSales test")
    public void testAviaSales() {
        getBrowser().goTo(DEFAULT_URL);
        TicketSearchPage ticketSearchPage = new TicketSearchPage();
        assertTrue(ticketSearchPage.state().waitForDisplayed(), "Ticket search not showing");
        ticketSearchPage.enterOriginCity(ORIGIN_CITY);
        ticketSearchPage.enterDestinationCity(DESTINATION_CITY);
        ticketSearchPage.enterDepartureDate(DEPARTURE_DAY);
        ticketSearchPage.enterReturnDate(RETURN_DAY);
        ticketSearchPage.entryTripClassEconomy();
        ticketSearchPage.clickSubmit();
        List<String> newTab = new ArrayList<>(getBrowser().getDriver().getWindowHandles());
        getBrowser().getDriver().switchTo().window(newTab.get(newTab.size() - 1));
        FoundTicketsPage foundTicketsPage = new FoundTicketsPage();
        assertTrue(foundTicketsPage.state().waitForDisplayed(), "Found tickets page not showing");
    }
}
