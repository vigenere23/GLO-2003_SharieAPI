package com.github.glo2003.controllers;

import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.helpers.ItemAlreadyExistsException;
import com.github.glo2003.helpers.ItemNotFoundException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Bookings;
import com.github.glo2003.models.Listing;
import com.github.glo2003.models.ListingsList;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.post;

public class ListingsController {

    public static ListingsDAO listingsDAO;

    public ListingsController() {
        setupRoutes();
        listingsDAO = new ListingsDAO();
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
            return listingsDAO.get(id);
        }
        catch (ItemNotFoundException e) {
            res.status(404);
            return ResponseHelper.errorAsJson(e.getMessage());
        }
    }

    public Object getAllListings(Request req, Response res) {
        return new ListingsList(listingsDAO.getAll());
    }

    public Object addListing(Request req, Response res) {
        try {
            Listing listing = ResponseHelper.deserializeJsonToObject(req.body(), Listing.class);
            long id = listingsDAO.save(listing);
            res.header("Location", String.format("/listings/%d", id));
            res.status(201);
            return ResponseHelper.EMPTY_RESPONSE;
        }
        catch (ItemAlreadyExistsException e) {
            res.status(400);
            return ResponseHelper.errorAsJson(e.getMessage());
        }
        catch (IOException e) {
            res.status(400);
            e.printStackTrace();
            return ResponseHelper.errorAsJson("Parameters are not valid for creating an object 'Listing'");
        }
        catch (Exception e) {
            res.status(400);
            e.printStackTrace();
            return ResponseHelper.errorAsJson("Request format was not valid");
        }
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