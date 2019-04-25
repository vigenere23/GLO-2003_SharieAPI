package com.github.glo2003.functional.listings;

import com.github.glo2003.functional.FunctionnalTest;
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

public class ListingsFunctionalTest extends FunctionnalTest {

    private List<Instant> instants;
    private Instant now;

    private String ID_REGEX;

    public ListingsFunctionalTest() {
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
        now = Instant.now();
        instants = new ArrayList<>();
    }

    @Before
    @After
    public void resetDao() {
        listingsDAO.reset();
    }

    @Test
    public void GET_listings_shouldReturn200() {
        ListingsFunctionalHelper.getAllListings()
        .then()
            .statusCode(200);
    }

    @Test
    public void givenInvalidListing_PostListing_shouldReturn400WithError() throws Exception {
        SimpleObject invalidListing = new SimpleObject();
        ListingsFunctionalHelper.postListing(invalidListing)
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturn201() {
        ListingsFunctionalHelper.postValidListing()
        .then()
            .statusCode(201);
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturnEmptyBody() {
        ListingsFunctionalHelper.postValidListing()
        .then()
            .body(emptyOrNullString());
    }

    @Test
    public void givenValidListing_POSTlistings_shouldReturnValidLocationHeader() {
        ListingsFunctionalHelper.postValidListing()
        .then()
            .header("Location", matchesPattern("^/listings/" + ID_REGEX + "$"));
    }

    @Test
    public void givenInvalidListing_POSTlistings_shouldReturn400WithErrorField() {
        ListingsFunctionalHelper.postListing("")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenInvalidJson_POSTlistings_shouldReturn400WithErrorField() {
        ListingsFunctionalHelper.postListing("{")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenNewServer_GETlistingWithAnyId_shouldReturn404WithErrorField() {
        ListingsFunctionalHelper.getListing("1000")
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenPostValidListing_GETlistingWithReturnedLocationId_shouldReturn200() {
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();
        ListingsFunctionalHelper.getListing(id)
        .then()
            .statusCode(200);
    }

    @Test
    public void givenaddRating_shouldReturn204() {
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();
        ListingsFunctionalHelper.addRating(id,4)
                .then()
                .statusCode(204);
    }

    @Test
    public void givenPostValidListing_GETlistingWithReturnedLocationId_shouldReturnValidListingDTO() throws JsonSerializingException {
        String validListingJson = ListingsFunctionalHelper.getListingDTOAsJson(ListingsFunctionalHelper.validListing);
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();        
        ListingsFunctionalHelper.getListing(id)
            .then()
            .body(equalTo(validListingJson));
    }

    @Test
    public void givenNewServer_GETlistings_shouldReturn200() {
        ListingsFunctionalHelper.getAllListings()
        .then()
            .statusCode(200);
    }

    @Test
    public void givenNewServer_GETlistings_shouldReturnListingsEmpty() {
        ListingsFunctionalHelper.getAllListings()
        .then()
            .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenPostTwoValidListings_GETlistings_shouldReturnTwoListings() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing();

        ListingsFunctionalHelper.getAllListings()
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostSameValidListingTwice_GETlistingsSpecificDateWithin7Days_shouldReturnThoseTwoListings() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing();
        
        ListingsFunctionalHelper.getAllListingsWithSpecificDate(now.toString().split("T")[0])
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostTwoValidListingsAndPastDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.getAllListingsWithSpecificDate("1950-01-01")
                .then()
                .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenInvalidDate_GETlistingsSpecificDate_shouldReturnNoListings() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing();
        
        ListingsFunctionalHelper.getAllListingsWithSpecificDate("")
        .then()
            .statusCode(400)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenPostTwoValidListingsWithDifferentTitle_GETAllListingsSpecificTitle_shouldReturnListOfSize1() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing2();

        String title = ListingsFunctionalHelper.validListing.getTitle();

        ListingsFunctionalHelper.getAllListingsWithSpecificTitle(title)
        .then()
            .body("listings", iterableWithSize(1));
    }

    @Test
    public void givenPostTwoValidListingsWithSameTitleAndOneOther_GETAllListingsSpecificTitle_shouldReturnListOfSize2() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing2();
        ListingsFunctionalHelper.postValidListing2();

        String title = ListingsFunctionalHelper.validListing2.getTitle();

        ListingsFunctionalHelper.getAllListingsWithSpecificTitle(title)
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenPostThreeValidListings_GETAllListingsWithNonExistantTitle_shouldReturnEmptyList() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing2();
        ListingsFunctionalHelper.postValidListing2();

        ListingsFunctionalHelper.getAllListingsWithSpecificTitle("This title doesn't exist")
        .then()
            .body("listings", iterableWithSize(0));
    }
    
    @Test
    public void givenPostValidListings_GETAllListingWithNonExistentCategory_shouldReturnEmptyList() {
        ListingsFunctionalHelper.postValidListing();

        ListingsFunctionalHelper.getAllListingsWithSpecificCategory("a-random-category")
        .then()
            .body("listings", iterableWithSize(0));
    }

    @Test
    public void givenPostTwoValidListingsWithDifferentCategories_GETAllListingWithOneCategory_shouldReturnListOfSize1() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing2();

        String category = ListingsFunctionalHelper.validListing.getCategory().name();

        ListingsFunctionalHelper.getAllListingsWithSpecificCategory(category)
        .then()
            .body("listings", iterableWithSize(1));
    }

    @Test
    public void givenPostSameTwoValidListings_GETAllListingWithPostedCategory_shouldReturnListOfSize2() {
        ListingsFunctionalHelper.postValidListing();
        ListingsFunctionalHelper.postValidListing();

        String category = ListingsFunctionalHelper.validListing.getCategory().name();

        ListingsFunctionalHelper.getAllListingsWithSpecificCategory(category)
        .then()
            .body("listings", iterableWithSize(2));
    }

    @Test
    public void givenNonExistingListingId_POSTbook_shouldReturn404WithError() {
        instants.add(now);

        ListingsFunctionalHelper.bookListing("1000", instants)
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void POSTbook_shouldReturn204() throws Exception {
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();
        instants.add(now);

        ListingsFunctionalHelper.bookListing(id, instants)
        .then()
            .statusCode(204)
            .body(emptyOrNullString());
    }

    @Test
    public void POSTbook_shouldRemoveAnAvailabilty() throws Exception {
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();
        instants.add(now);

        ListingsFunctionalHelper.bookListing(id, instants);
        ListingsFunctionalHelper.getListing(id)
        .then()
            .body("availabilities", iterableWithSize(6));
    }

    @Test
    public void givenPOSTbook_bookSameAvailabilityAgain_shouldReturn404WithError() {
        String id = ListingsFunctionalHelper.getIdOfValidPostedListing();
        instants.add(now);

        ListingsFunctionalHelper.bookListing(id, instants);
        ListingsFunctionalHelper.bookListing(id, instants)
        .then()
            .statusCode(404)
            .body("error", not(emptyOrNullString()));
    }

    @Test
    public void givenPOSTValidListing_getAllListingsWithSpecificTitleCategoryDate_shouldReturnListOfSize1() {
        ListingsFunctionalHelper.postValidListing();

        get(
            "listings?title={title}&category={category}&date={date}",
            ListingsFunctionalHelper.validListing.getTitle(),
            ListingsFunctionalHelper.validListing.getCategory().name(),
            DateTimeHelper.fromInstantToDateString(now)
        )
        .then()
            .body("listings", iterableWithSize(1));
    }

}
