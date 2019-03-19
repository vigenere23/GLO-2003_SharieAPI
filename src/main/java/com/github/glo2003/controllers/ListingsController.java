package com.github.glo2003.controllers;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.dtos.ListingDTO;
import com.github.glo2003.dtos.ListingDTOList;
import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.exceptions.JsonDeserializingException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Bookings;
import com.github.glo2003.models.Listing;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

public class ListingsController {

    static ListingsDAO listingsDAO;

    public ListingsController() {
        setupRoutes();
        listingsDAO = new InMemoryListingsDAO();
    }

    private void setupRoutes() {
        get("/listings", this::getAllListings, ResponseHelper::serializeObjectToJson);
        post("/listings", this::addListing, ResponseHelper::serializeObjectToJson);

        get("/listings/:id", this::getListing, ResponseHelper::serializeObjectToJson);

        post("/listings/:id/book", this::bookListing, ResponseHelper::serializeObjectToJson);
    }

    public Object getListing(Request req, Response res) {
        String stringId = req.params(":id");
        long id;
        try {
            id = Long.parseLong(stringId);
        }
        catch (Exception e) {
            res.status(400);
            return ResponseHelper.errorAsJson(String.format("Id '%s' should be of type 'long'", stringId));
        }

        try {
            Listing listing = listingsDAO.get(id);
            return new ListingDTO(listing);
        }
        catch (ItemNotFoundException e) {
            res.status(404);
            return ResponseHelper.errorAsJson(e.getMessage());
        }
    }

    public Object getAllListings(Request req, Response res) {
        return new ListingDTOList(listingsDAO.getAll());
    }

    public Object addListing(Request req, Response res) throws ItemAlreadyExistsException, JsonDeserializingException {
        Listing listing = ResponseHelper.deserializeJsonToObject(req.body(), Listing.class);
        long id = listingsDAO.save(listing);
        res.header("Location", String.format("/listings/%d", id));
        res.status(201);
        return ResponseHelper.EMPTY_RESPONSE;
    }

    public Object bookListing(Request req, Response res) throws Exception {

        // Get le id et le listing correspondant, possibilité d'utiliser getListing? (Faut prob ajouter qql truc à mth)
        String stringId = req.params(":id");
        long id;

        try {
            id = Long.parseLong(stringId);
        }
        catch (Exception e) {
            res.status(400);
            return ResponseHelper.errorAsJson(String.format("Id '%s' should be of type 'long'", stringId));
        }

        Bookings bookings = ResponseHelper.deserializeJsonToObject(req.body(), Bookings.class);

        try {
            Listing listing = listingsDAO.get(id);
            listing.book(bookings.getBookings());

            res.status(204);
            return ResponseHelper.EMPTY_RESPONSE;
        }
        catch (ItemNotFoundException e) {
            res.status(402);
            return ResponseHelper.errorAsJson(e.getMessage());
        }
    }
}