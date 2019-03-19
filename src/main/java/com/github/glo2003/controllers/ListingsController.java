package com.github.glo2003.controllers;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.dtos.ListingDTOList;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Bookings;
import com.github.glo2003.models.Listing;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class ListingsController {

    static ListingsDAO listingsDAO;

    public ListingsController() {
        setupRoutes();
        listingsDAO = new InMemoryListingsDAO();
    }

    private void setupRoutes() {
        path("/listings", () -> {
            get("", this::getAllListings, ResponseHelper::serializeObjectToJson);
            post("", this::addListing, ResponseHelper::serializeObjectToJson);
            path("/:id", () -> {
                before("", this::validateListing);
                get("", this::getListing, ResponseHelper::serializeObjectToJson);
                post("/book", this::bookListing, ResponseHelper::serializeObjectToJson);
            });
        });
    }

    private long parseIdFromParam(String stringId) throws ParameterParsingException {
        try {
            return Long.parseLong(stringId);
        }
        catch (NumberFormatException e) {
            throw new ParameterParsingException("id", "long");
        }
    }

    private void validateListing(Request req, Response res) throws Exception {
        Long id = parseIdFromParam(req.params("id"));
        Listing listing = listingsDAO.get(id);
        req.attribute("listing", listing);
    }

    public Object getListing(Request req, Response res) throws Exception {
        Listing listing = req.attribute("listing");
        return new ListingDTO(listing);
    }

    public Object getAllListings(Request req, Response res) {
        return new ListingDTOList(listingsDAO.getAll());
    }

    public Object addListing(Request req, Response res) throws Exception {
        Listing listing = ResponseHelper.deserializeJsonToObject(req.body(), Listing.class);
        long id = listingsDAO.save(listing);
        res.header("Location", String.format("/listings/%d", id));
        res.status(201);
        return ResponseHelper.EMPTY_RESPONSE;
    }

    public Object bookListing(Request req, Response res) throws Exception {
        Listing listing = req.attribute("listing");
        Bookings bookings = ResponseHelper.deserializeJsonToObject(req.body(), Bookings.class);
        listing.book(bookings.getBookings());

        res.status(204);
        return ResponseHelper.EMPTY_RESPONSE;
    }
}