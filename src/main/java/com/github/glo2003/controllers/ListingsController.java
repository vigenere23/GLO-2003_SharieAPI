package com.github.glo2003.controllers;

import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.dtos.ListingDTOList;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.filters.ListingsFilter;
import com.github.glo2003.helpers.DateTimeHelper;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Bookings;
import com.github.glo2003.models.Listing;
import spark.Request;
import spark.Response;

import java.time.Instant;
import java.util.List;

import static spark.Spark.*;

public class ListingsController implements Controller {

    static ListingsDAO listingsDAO;

    public ListingsController(ListingsDAO listingsDAO) {
        setupRoutes();
        ListingsController.listingsDAO = listingsDAO;
    }

    public void setupRoutes() {
        path("/listings", () -> {
            get("", this::getAllListings, ResponseHelper::serializeObjectToJson);
            post("", this::addListing, ResponseHelper::serializeObjectToJson);
            path("/:id", () -> {
                before("", this::validateListing);
                before("/*", this::validateListing);
                get("", this::getListing, ResponseHelper::serializeObjectToJson);
                post("/book", this::bookListing, ResponseHelper::serializeObjectToJson);
            });
        });
    }
/*
    private LocalDate parseDateFromParam(String stringDate) throws ParameterParsingException {
        try {
            return LocalDate.parse(stringDate);
        } catch (DateTimeParseException e) {
            throw new ParameterParsingException("date", "LocalDate");
        }
    }
*/
    private Instant parseDateFromParam(String stringDate) throws ParameterParsingException {
        try {
            return Instant.parse(stringDate + "T00:00:00Z");
        } catch (Exception e) {
            throw new ParameterParsingException("date", "Instant");
        }
    }

    private void validateListing(Request req, Response res) throws Exception {
        String id = req.params("id");
        Listing listing = listingsDAO.get(id);
        req.attribute("listing", listing);
    }

    private Object getListing(Request req, Response res) throws Exception {
        Listing listing = req.attribute("listing");
        return new ListingDTO(listing);
    }

    private Object getAllListings(Request req, Response res) throws Exception{
        String dateString = req.queryParams("date");
        String title = req.queryParams("title");

        List<Listing> listings = listingsDAO.getAll();

        if (dateString != null) {
            Instant date = parseDateFromParam(dateString);
            listings = ListingsFilter.filterByDate(listings, DateTimeHelper.removeTimeFromInstant(date));
        }
        
        if (title != null) {
            listings = ListingsFilter.filterByTitle(listings, title);
        }

        return new ListingDTOList(listings);
    }

    private Object addListing(Request req, Response res) throws Exception {
        Listing listing = ResponseHelper.deserializeJsonToObject(req.body(), Listing.class);
        String id = listingsDAO.save(listing);
        res.header("Location", String.format("/listings/%s", id));
        res.status(201);
        return ResponseHelper.EMPTY_RESPONSE;
    }

    private Object bookListing(Request req, Response res) throws Exception {
        Listing listing = req.attribute("listing");
        Bookings bookings = ResponseHelper.deserializeJsonToObject(req.body(), Bookings.class);
        listing.book(bookings.getBookings());

        res.status(204);
        return ResponseHelper.EMPTY_RESPONSE;
    }

}
