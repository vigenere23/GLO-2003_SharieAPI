package com.github.glo2003.controllers;

import com.github.glo2003.FunctionnalTest;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.daos.MorphiaListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.exceptions.JsonSerializingException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.stubs.BookingsPostDTO;
import com.github.glo2003.stubs.ListingPostDTO;
import com.github.glo2003.models.Listing;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static com.github.glo2003.controllers.ListingsController.listingsDAO;

public class ListingsControllerTest extends FunctionnalTest {

    private Listing validListing;
    private Listing validListing2;
    private List<Instant> instants;
    private Instant now;

    @Before
    public void setupValidObjects() {
        validListing = new Listing(
            "A nice listing",
            "Splendid offer right here!",
            "Jane Smith",
            "8197771111",
            "jane.smith@gmail.com");
        validListing2 = new Listing(
            "Another nice listing",
            "Yet another splendid offer",
            "John Smith",
            "4189990000",
            "john.smith@gmail.com"
        );
        now = Instant.now();
        instants = new ArrayList<>();
    }

    @Before
    public void resetDao() {
        String profile = Optional.ofNullable(System.getenv("SHARIE_PROFILE")).orElse("dev");

        if(profile.equals("dev")){
            listingsDAO = new InMemoryListingsDAO();
        }else if(profile.equals("prod") || profile.equals("test")){
            listingsDAO = new MorphiaListingsDAO();
        }
        else{
            throw new IllegalArgumentException("Unknown profile");
        }
    }

    private Response getAllListings() {
        return get("/listings");
    }

    private Response getAllListingsSpecificDate(String date) {
        return get("/listings?date={date}", date);
    }

    private Response getListing(String id) {
        return get("/listings/{id}", id);
    }

    private Response postValidListing() {
        return postListing(new ListingPostDTO(validListing));
    }

    private Response postValidListing2() {
        return postListing(new ListingPostDTO(validListing2));
    }

    private Response postListing(Object body) {
        return
            given()
                .contentType("application/json")
                .body(body)
            .when()
                .post("/listings");
    }

    private Response bookListing(String listingId, List<Instant> bookingList) {
        BookingsPostDTO bookingsPostDTO = new BookingsPostDTO(bookingList);
        return
            given()
                .contentType("application/json")
                .body(bookingsPostDTO)
            .when()
                .post("/listings/" + listingId + "/book");
    }

    private String getIdOfValidPostedListing() {
        String[] splittedLocationHeader = postValidListing().header("Location").split("/");
        return splittedLocationHeader[splittedLocationHeader.length - 1];
    }

    private String getListingDTOAsJson(Listing listing) throws JsonSerializingException {
        return ResponseHelper.serializeObjectToJson(new ListingDTO(listing));
    }

    @Test
    public void GET_listings_shouldReturn200() {
        getAllListings()
        .then()
            .statusCode(200);
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturn201() {
        postValidListing()
        .then()
            .statusCode(201);
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturnEmptyBody() {
        postValidListing()
        .then()
            .body(emptyOrNullString());
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturnValidLocationHeader() {
        postValidListing()
        .then()
            .header("Location", matchesPattern("^/listings/[0-9]+$"));
    }

    @Test
    public void givenInvalidListing_POSTlistings_shouldReturn400WithErrorField() {
        postListing("")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenInvalidJson_POSTlistings_shouldReturn400WithErrorField() {
        postListing("{")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void GETlistingWithInvalidId_shouldReturn400WithErrorField() {
        getListing("abc")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenNewServer_GETlistingWithAnyId_shouldReturn404WithErrorField() {
        getListing("1000")
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenPostValidListing_GETlistingWithReturnedLocationId_shouldReturn200() {
        getListing(getIdOfValidPostedListing())
        .then()
            .statusCode(200);
    }

    @Test
    public void givenPostValidListing_GETlistingWithReturnedLocationId_shouldReturnValidListingDTO() throws JsonSerializingException {
        String validListingJson = getListingDTOAsJson(validListing);
        getListing(getIdOfValidPostedListing())
            .then()
            .body(equalTo(validListingJson));
    }

    @Test
    public void givenNewServer_GETlistings_shouldReturn200() {
        getAllListings()
        .then()
            .statusCode(200);
    }

    @Test
    public void givenNewServer_GETlistings_shouldReturnListingsEmpty() {
        getAllListings()
            .then()
            .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenPostTwoValidListings_GETlistings_shouldReturnTwoListings() {
        postValidListing();
        postValidListing();

        getAllListings()
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostTwoValidListingsAndValidDate_GETlistingsSpecificDate_shouldReturnTwoListings() {
        postValidListing();
        postValidListing();
        getAllListingsSpecificDate(LocalDate.now().toString())
                .then()
                .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostTwoValidListingsAndPastDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        postValidListing();
        postValidListing();
        getAllListingsSpecificDate("1950-01-01")
                .then()
                .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenInvalidDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        postValidListing();
        postValidListing();
        getAllListingsSpecificDate("")
                .then()
                .statusCode(400)
                .body("error", not(emptyOrNullString()));
    }

    // TODO test if getAllListings contains the same 2 posted listings

    @Test
    public void givenNonExistingListingId_POSTbook_shouldReturn404WithError() {
        instants.add(now);

        bookListing("1000", instants)
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void POSTbook_shouldReturn204() throws Exception {
        String id = getIdOfValidPostedListing();
        instants.add(now);

        bookListing(id, instants)
        .then()
            .statusCode(204)
            .body(emptyOrNullString());
    }

    @Test
    public void POSTbook_shouldRemoveAnAvailabilty() throws Exception {
        String id = getIdOfValidPostedListing();
        instants.add(now);

        bookListing(id, instants);
        getListing(id)
        .then()
            .body("availabilities", iterableWithSize(6));
    }

    @Test
    public void givenPOSTbook_bookSameAvailabilityAgain_shouldReturn404WithError() {
        String id = getIdOfValidPostedListing();
        instants.add(now);

        bookListing(id, instants);
        bookListing(id, instants)
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

}
