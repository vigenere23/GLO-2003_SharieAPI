package com.github.glo2003.controllers;

import com.github.glo2003.helpers.ItemAlreadyExistsException;
import com.github.glo2003.helpers.ItemNotFoundException;
import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
import com.github.glo2003.models.ListingsList;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static com.github.glo2003.APIServer.listingsDAO;

public class ListingsController {

    public static Object getListing(Request req, Response res) {
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

    public static Object getAllListings(Request req, Response res) {
        return new ListingsList(listingsDAO.getAll());
    }

    public static Object addListing(Request req, Response res) {
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
            return ResponseHelper.errorAsJson("Parameters are not valid for creating an object 'Listing'");
        }
        catch (Exception e) {
            res.status(400);
            return ResponseHelper.errorAsJson("Request format was not valid");
        }
    }

    public static Object bookListing(Request req, Response res) {
        // TODO
        return ResponseHelper.EMPTY_RESPONSE;
    }
}