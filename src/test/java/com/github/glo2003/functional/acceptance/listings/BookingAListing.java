package com.github.glo2003.functional.acceptance.listings;

import com.github.glo2003.functional.acceptance.AcceptanceTest;
import com.github.glo2003.functional.listings.ListingsFunctionalHelper;
import com.github.glo2003.models.Listing;
import com.github.glo2003.stubs.ListingPostDTO;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static io.restassured.RestAssured.given;

public class BookingAListing extends AcceptanceTest {

  private String lastPostedListingId;

  public BookingAListing() {
    lastPostedListingId = "";
  }

  @Given("There's a booking existing with a specific ID and with the next 7 days \\\\(including today) available to book")
  public void postValidListing() {
    lastPostedListingId = ListingsFunctionalHelper.getIdOfValidPostedListing();
  }

  @When("I book that listing {int} day\\(s) from now, which is in the range of the next 7 days \\\\(including today)")
  public void BookingExistingListingAtAvailableTime(Integer nbOfDaysFromNow) {
    return;
  }

  @When("I book that listing {int} day\\(s) from now, which is NOT in the range of the next 7 days \\\\(including today)")
  public void BookingExistingListingAtUnavailableTime(Integer nbOfDaysFromNow) {
    return;
  }

}
