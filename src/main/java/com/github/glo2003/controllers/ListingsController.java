package com.github.glo2003.controllers;

import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
import spark.Request;
import spark.Response;

import static com.github.glo2003.APIServer.listingsDAO;

public class ListingsController {

    public static Object getListing(Request req, Response res) {
        String stringId = req.params(":id");
        long id;
        try {
            id = Long.parseLong(stringId);
        }
        catch (Exception e) {
            return new ResponseHelper.ResponseError(String.format("Id '%s' should be of type 'long'", stringId));
        }

        Listing listing = listingsDAO.get(id);
        return ResponseHelper.returnNotNullObjectOrError(
                res, listing, 404,
                String.format("No listing found with id '%d'", id));
    }

    public static Object getAllListings(Request req, Response res) {
        // TODO
        return ResponseHelper.EMPTY_RESPONSE;
    }

    public static String addListing(Request req, Response res) {
        Listing listing = new Listing(/*TODO add post data as a Map or Object or...*/);
        long id = listingsDAO.save(listing);
        res.header("Location", String.format("/listings/%d", id));
        res.status(201);
        return ResponseHelper.EMPTY_RESPONSE;
    }

    public static Object bookListing(Request req, Response res) {
        // TODO
        return ResponseHelper.EMPTY_RESPONSE;
    }
}