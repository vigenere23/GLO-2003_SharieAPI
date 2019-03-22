package com.github.glo2003.controllers;

import com.github.glo2003.FunctionnalTest;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.exceptions.JsonSerializingException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.stubs.ListingPostDTO;
import com.github.glo2003.models.Listing;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static com.github.glo2003.controllers.ListingsController.listingsDAO;

public class ListingsControllerTest extends FunctionnalTest {

    private Listing validListing;
    private Listing validListing2;

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
    }

    @Before
    public void resetDao() {
        listingsDAO = new InMemoryListingsDAO();
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

    private String getIdOfValidPostedListing() {
        String[] splittedLocationHeader = postValidListing().header("Location").split("/");
        return splittedLocationHeader[splittedLocationHeader.length - 1];
    }

    private String getListingDTOAsJson(Listing listing) throws JsonSerializingException {
        return ResponseHelper.serializeObjectToJson(new ListingDTO(listing));
    }

    private Response getAllListings() {
        return get("/listings");
    }

    private Response getListing(String id) {
        return get("/listings/{id}", id);
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
            .body(isEmptyOrNullString());
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
            .body("error", not(isEmptyOrNullString()));
    }

    @Test
    public void givenInvalidJson_POSTlistings_shouldReturn400WithErrorField() {
        postListing("{")
        .then()
            .statusCode(400)
            .body("error", not(isEmptyOrNullString()));
    }

    @Test
    public void GETlistingWithInvalidId_shouldReturn400WithErrorField() {
        getListing("abc")
        .then()
            .statusCode(400)
            .body("error", not(isEmptyOrNullString()));
    }

    @Test
    public void givenNewServer_GETlistingWithAnyId_shouldReturn404WithErrorField() {
        getListing("1000")
        .then()
            .statusCode(404)
            .body("error", not(isEmptyOrNullString()));
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

    // TODO test if getAllListings contains the same 2 posted listings

}
