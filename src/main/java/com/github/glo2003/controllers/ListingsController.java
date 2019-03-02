package com.github.glo2003.controllers;

import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
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

    public static Object addListing(Request req, Response res) {
        //TEST USEFULL POUR MONTRER COMMENT TESTER UN OBJET RECU
        // String test = "{  \"title\": \"\",  \"description\": \"\",  \"owner\": {  \"name\": \"\",  \"phoneNumber\": \"\",  \"email\": \"\"  }}";
        // Object TestedObject = ResponseHelper.isParameterValid(test, Listing.class);
        //Listing listing = new Listing(/*TODO add post data as a Map or Object or...*/);
        try {
            Listing listing = ResponseHelper.isParameterValid(req.body(), Listing.class);
            long id = listingsDAO.save(listing);
            res.header("Location", String.format("/listings/%d", id));
            res.status(201);
            return ResponseHelper.EMPTY_RESPONSE;
        }
        catch (IOException e) {
            res.status(400);
            return new ResponseHelper.ResponseError("Les paramêtres ne sont pas valides pour un objet Listing");
        }
        catch (Exception e) {
            res.status(400);
            return new ResponseHelper.ResponseError("Le format de la requête est invalide");
        }
    }

    public static Object bookListing(Request req, Response res) {
        // TODO
        return ResponseHelper.EMPTY_RESPONSE;
    }
}