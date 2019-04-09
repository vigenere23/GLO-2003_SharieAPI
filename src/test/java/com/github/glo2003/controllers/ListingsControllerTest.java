package com.github.glo2003.controllers;

import com.github.glo2003.FunctionnalTest;
import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.MorphiaListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.exceptions.JsonSerializingException;
import com.github.glo2003.helpers.DateTimeHelper;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
import com.github.glo2003.stubs.BookingsPostDTO;
import com.github.glo2003.stubs.ListingPostDTO;
import com.github.glo2003.stubs.SimpleObject;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.glo2003.controllers.ListingsController.listingsDAO;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class ListingsControllerTest extends FunctionnalTest {

    private Listing validListing;
    private Listing validListing2;
    private List<Instant> instants;
    private Instant now;

    private String ID_REGEX;

    public ListingsControllerTest() {
        String profile = Optional.ofNullable(System.getenv("SHARIE_PROFILE")).orElse("dev");

        if (profile.equals("dev")) {
            ID_REGEX = "[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}";
            listingsDAO = new InMemoryListingsDAO();
        } else if (profile.equals("test")) {
            ID_REGEX = "[0-9a-f]{24}";
            listingsDAO = new MorphiaListingsDAO();
        } else {
            throw new IllegalArgumentException("Unknown profile");
        }
    }

    @Before
    public void setupValidObjects() {
        validListing = new Listing(
            "Such a nice listing",
            "Splendid offer right here!",
            "Outdoors",
            "Jane Smith",
            "8197771111",
            "jane.smith@gmail.com");
        validListing2 = new Listing(
            "Yet another nice listing",
            "Yet another splendid offer",
            "Kitchen",
            "John Smith",
            "4189990000",
            "john.smith@gmail.com");

        now = Instant.now();
        instants = new ArrayList<>();
    }

    @Before
    @After
    public void resetDao() {
        listingsDAO.reset();
    }

    protected Response getAllListings() {
        return get("/listings");
    }

    protected Response getAllListingsWithSpecificDate(String date) {
        return get("/listings?date={date}", date);
    }

    private Response getAllListingsWithSpecificTitle(String title) {
        return get("/listings?title={title}", title);
    }

    private Response getAllListingsWithSpecificCategory(String category) {
        return get("/listings?category={category}", category);
    }

    protected Response addRating(String id, Integer score) {
        return get("/listings/{id}/rate/{score}", id, score);
    }

    protected Response getListing(String id) {
        return get("/listings/{id}", id);
    }

    protected Response postValidListing() {
        return postListing(new ListingPostDTO(validListing));
    }

    protected Response postValidListing2() {
        return postListing(new ListingPostDTO(validListing2));
    }

    protected Response postListing(Object body) {
        return
            given()
                .contentType("application/json")
                .body(body)
            .when()
                .post("/listings");
    }

    protected Response bookListing(String listingId, List<Instant> bookingList) {
        BookingsPostDTO bookingsPostDTO = new BookingsPostDTO(bookingList);
        return
            given()
                .contentType("application/json")
                .body(bookingsPostDTO)
            .when()
                .post("/listings/" + listingId + "/book");
    }

    protected String getIdOfValidPostedListing() {
        String[] splittedLocationHeader = postValidListing().header("Location").split("/");
        return splittedLocationHeader[splittedLocationHeader.length - 1];
    }

    protected String getListingDTOAsJson(Listing listing) throws JsonSerializingException {
        return ResponseHelper.serializeObjectToJson(new ListingDTO(listing));
    }

    @Test
    public void GET_listings_shouldReturn200() {
        getAllListings()
        .then()
            .statusCode(200);
    }

    @Test
    public void givenInvalidListing_PostListing_shouldReturn400WithError() throws Exception {
        SimpleObject invalidListing = new SimpleObject();
        postListing(invalidListing)
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
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
            .header("Location", matchesPattern("^/listings/" + ID_REGEX + "$"));
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
    public void givenaddRating_shouldReturn204() {
        String id = getIdOfValidPostedListing();
        addRating(id,4)
                .then()
                .statusCode(204);
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
    public void givenPostSameValidListingTwice_GETlistingsSpecificDateWithin7Days_shouldReturnThoseTwoListings() {
        postValidListing();
        postValidListing();
        
        getAllListingsWithSpecificDate(now.toString().split("T")[0])
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostTwoValidListingsAndPastDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        postValidListing();
        postValidListing();
        getAllListingsWithSpecificDate("1950-01-01")
                .then()
                .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenInvalidDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        postValidListing();
        postValidListing();
        
        getAllListingsWithSpecificDate("")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenPostTwoValidListingsWithDifferentTitle_GETAllListingsSpecificTitle_shouldReturnListOfSize1() {
        postValidListing();
        postValidListing2();

        getAllListingsWithSpecificTitle(validListing.getTitle())
        .then()
            .body("listings", iterableWithSize(1));
    }

    @Test
    public void givenPostTwoValidListingsWithSameTitleAndOneOther_GETAllListingsSpecificTitle_shouldReturnListOfSize2() {
        postValidListing();
        postValidListing2();
        postValidListing2();

        getAllListingsWithSpecificTitle(validListing2.getTitle())
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostThreeValidListings_GETAllListingsWithNonExistantTitle_shouldReturnEmptyList() {
        postValidListing();
        postValidListing2();
        postValidListing2();

        getAllListingsWithSpecificTitle("This title doesn't exist")
        .then()
            .body("listings", iterableWithSize(0));
    }
    
    @Test
    public void givenPostValidListings_GETAllListingWithNonExistentCategory_shouldReturnEmptyList() {
        postValidListing();

        getAllListingsWithSpecificCategory("a-random-category")
        .then()
            .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenPostTwoValidListingsWithDifferentCategories_GETAllListingWithOneCategory_shouldReturnListOfSize1() {
        postValidListing();
        postValidListing2();

        getAllListingsWithSpecificCategory(validListing.getCategory().name())
        .then()
            .body("listings", iterableWithSize(1));
    }

    @Test
    public void givenPostSameTwoValidListings_GETAllListingWithPostedCategory_shouldReturnListOfSize2() {
        postValidListing();
        postValidListing();

        getAllListingsWithSpecificCategory(validListing.getCategory().name())
        .then()
            .body("listings", iterableWithSize(2));
    }

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

    @Test
    public void givenPOSTValidListing_getAllListingsWithSpecificTitleCategoryDate_shouldReturnListOfSize1() {
        postValidListing();

        get(
            "listings?title={title}&category={category}&date={date}",
            validListing.getTitle(),
            validListing.getCategory().name(),
            DateTimeHelper.fromInstantToDateString(now)
        )
        .then()
            .body("listings", iterableWithSize(1));
    }

}
