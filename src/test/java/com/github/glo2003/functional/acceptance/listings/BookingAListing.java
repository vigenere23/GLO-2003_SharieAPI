package com.github.glo2003.functional.acceptance.listings;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.MorphiaListingsDAO;
import com.github.glo2003.functional.acceptance.AcceptanceTest;
import com.github.glo2003.functional.listings.ListingsFunctionalHelper;
import com.github.glo2003.helpers.DateTimeHelper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static com.github.glo2003.controllers.ListingsController.listingsDAO;

public class BookingAListing extends AcceptanceTest {

    private String lastPostedListingId;
    private Instant lastBookedTime;
    private Response lastResponse;

    public BookingAListing() {
        lastPostedListingId = "";

        String profile = Optional.ofNullable(System.getenv("SHARIE_PROFILE")).orElse("dev");

        if (profile.equals("dev")) {
            listingsDAO = new InMemoryListingsDAO();
        } else if (profile.equals("test")) {
            listingsDAO = new MorphiaListingsDAO();
        } else {
            throw new IllegalArgumentException("Unknown profile");
        }
    }

    private Response bookListingAtNbOfDaysFromNow(Integer nbOfDaysFromNow) {
        lastBookedTime = Instant.now().plus(nbOfDaysFromNow, ChronoUnit.DAYS);
        List<Instant> instants = new ArrayList<>();
        instants.add(lastBookedTime);
        return ListingsFunctionalHelper.bookListing(lastPostedListingId, instants);
    }

    @Given("I post a new listing")
    public void postValidListing() {
        lastPostedListingId = ListingsFunctionalHelper.getIdOfValidPostedListing();
    }

    @Given("That listing has the next {int} day\\(s) \\\\(including today) available for booking")
    public void checkLastListingAvailabilities(Integer nbOfDaysAvailable) {
        ListingsFunctionalHelper.getListing(lastPostedListingId)
        .then()
            .body("availabilities", iterableWithSize(nbOfDaysAvailable));
    }

    @When("I book that listing {int} day\\(s) from now, which is supposed to be available")
    public void BookingExistingListingAtAvailableTime(Integer nbOfDaysFromNow) {
        lastResponse = bookListingAtNbOfDaysFromNow(nbOfDaysFromNow);
    }

    @Then("It should return the status code {int}")
    public void it_should_return_the_status_code(Integer statusCode) {
        lastResponse
        .then()
            .statusCode(statusCode);
    }

    @Then("The response should be empty")
    public void the_response_should_be_empty() {
        lastResponse
        .then()
            .body(emptyOrNullString());
    }

    @Then("It should remove {int} day from that listing's availabilities")
    public void it_should_remove_day_from_that_listing_s_availabilities(Integer nbOfDaysRemoved) {
        ListingsFunctionalHelper.getListing(lastPostedListingId)
        .then()
            .body("availabilities", iterableWithSize(7 - nbOfDaysRemoved));
    }

    @Then("The removed day should be the one booked")
    public void it_should_make_that_day_no_more_available_in_that_listing_s_availabilities() {
        Instant trimedLastBookedTime = DateTimeHelper.removeTimeFromInstant(lastBookedTime);
        ListingsFunctionalHelper.getListing(lastPostedListingId)
        .then()
            .body("availabilities", not(contains(trimedLastBookedTime.toString())));
    }

    @When("I book that listing {int} day\\(s) from now, which is NOT supposed to be available")
    public void BookingExistingListingAtUnavailableTime(Integer nbOfDaysFromNow) {
        lastResponse = bookListingAtNbOfDaysFromNow(nbOfDaysFromNow);    
    }

    @Then("The response should contain an error")
    public void the_response_should_contain_an_error() {
        lastResponse
        .then()
            .body("error", not(emptyOrNullString()));
    }

    @Then("The response error should say {string}")
    public void the_error_should_say(String errorMessage) {
        lastResponse
        .then()
            .body("error", equalTo(errorMessage));
    }

    @Given("There are no existing listings")
    public void there_are_no_existing_listings() {
        listingsDAO.reset();
    }

    @When("I try to book a non-existing listing \\\\(with id {string})")
    public void BookingNonExistingListing(String id) {
        lastResponse = ListingsFunctionalHelper.bookListing(id, new ArrayList<>());
    }

}
