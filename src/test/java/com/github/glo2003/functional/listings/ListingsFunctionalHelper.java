package com.github.glo2003.functional.listings;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.List;

import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.exceptions.JsonSerializingException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
import com.github.glo2003.stubs.BookingsPostDTO;
import com.github.glo2003.stubs.ListingPostDTO;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ListingsFunctionalHelper {

  public static final Listing validListing = new Listing(
    "Such a nice listing",
    "Splendid offer right here!",
    "Outdoors",
    "Jane Smith",
    "8197771111",
    "jane.smith@gmail.com");

  public static final Listing validListing2 = new Listing(
    "Yet another nice listing",
    "Yet another splendid offer",
    "Kitchen",
    "John Smith",
    "4189990000",
    "john.smith@gmail.com");

  public static Response getAllListings() {
    return get("/listings");
  }

  public static Response getAllListingsWithSpecificDate(String date) {
      return get("/listings?date={date}", date);
  }

  public static Response getAllListingsWithSpecificTitle(String title) {
      return get("/listings?title={title}", title);
  }

  public static Response getAllListingsWithSpecificCategory(String category) {
      return get("/listings?category={category}", category);
  }

  public static Response addRating(String id, Integer score) {
      return get("/listings/{id}/rate/{score}", id, score);
  }

  public static Response getListing(String id) {
      return get("/listings/{id}", id);
  }

  public static Response postValidListing() {
      return postListing(new ListingPostDTO(validListing));
  }

  public static Response postValidListing2() {
      return postListing(new ListingPostDTO(validListing2));
  }

  public static Response postListing(Object body) {
      return
          given()
              .contentType("application/json")
              .body(body)
          .when()
              .post("/listings");
  }

  public static Response bookListing(String listingId, List<Instant> bookingList) {
      BookingsPostDTO bookingsPostDTO = new BookingsPostDTO(bookingList);
      return
          given()
              .contentType("application/json")
              .body(bookingsPostDTO)
          .when()
              .post("/listings/" + listingId + "/book");
  }

  public static String getIdOfValidPostedListing() {
      String[] splittedLocationHeader = postValidListing().header("Location").split("/");
      return splittedLocationHeader[splittedLocationHeader.length - 1];
  }

  public static String getListingDTOAsJson(Listing listing) throws JsonSerializingException {
      return ResponseHelper.serializeObjectToJson(new ListingDTO(listing));
  }
}