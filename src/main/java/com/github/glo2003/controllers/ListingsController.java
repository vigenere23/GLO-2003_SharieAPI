package com.github.glo2003.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.models.Listing;
import spark.Request;
import spark.Response;

import static com.github.glo2003.APIServer.listingsDAO;

public class ListingsController {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    /**
     * @api {get} /listings/:id Get a single listing
     * @apiName getListing
     * @apiGroup Listings
     *
     * @apiParam {String} id Listing's ID
     *
     * @apiSuccess {String[]} availabilities List of date in format [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601). By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.
     * @apiSuccessExample Response
     * 200 HTTP Ok
     * {
     *   "listings": [{
     *     "title": ""::string,
     *     "description": ""::string,
     *     "availabilities": [ "2019-02-12T00:00:00Z"::string, ... ]
     *     "owner": {
     *       "name": ""::string,
     *       "phoneNumber": ""::string,
     *       "email": ""::string,
     *     }
     *   },
     *   ...
     *   ]
     * }
     */
    public static String getListing(Request req, Response res) {
        int id = 0;
        try {
            String stringId = req.params(":id");
            id = Integer.parseInt(stringId);
        }
        catch (Exception e) {
            System.out.println(String.format("Unable to convert id %s to int", id));
            return "";
        }

        Listing listing = listingsDAO.get(id);

        if (listing == null) {
            System.out.println(String.format("Unable to find listing with id %d", id));
            return "";
        }

        try {
            System.out.println("Sending response...");
            return jsonObjectMapper.writeValueAsString(listing);
        }
        catch (Exception e) {
            System.out.println("Unable to parse Listing as json");
            return "";
        }
    }

    /**
     * @api {get} /listings Get all listings
     * @apiName getAllListings
     * @apiGroup Listings
     *
     * @apiSuccess {String[]} availabilities List of date in format [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601). By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.
     * @apiSuccessExample Response
     * ###
     *
     * ```
     * 200 HTTP Ok
     * {
     *   "title": ""::string,
     *   "description": ""::string,
     *   "availabilities": [ "2019-02-12T00:00:00Z"::string, ... ]
     *   "owner": {
     *     "name": ""::string,
     *     "phoneNumber": ""::string,
     *     "email": ""::string,
     *   }
     * }
     * ```
     */
    public static String getAllListings(Request req, Response res) {
        // TODO
        return "";
    }

    /**
     * @api {post} /listings Add a new listing
     * @apiName addListing
     * @apiGroup Listings
     *
     * @apiDescription No body is returned, only the header `Location`.
     *
     * @apiParamExample Request
     * POST /listings
     * {
     *   "title": ""::string,
     *   "description": ""::string,
     *   "owner": {
     *     "name": ""::string,
     *     "phoneNumber": ""::string,
     *     "email": ""::string,
     *   }
     * }
     *
     * @apiSuccess (Created 201) {Number} id ID of the newly created listing
     * @apiSuccessExample Response
     *    HTTP 201 CREATED
     *    Location: /listings/:id
     */
    public static String addListing(Request req, Response res) {
        Listing listing = new Listing(/*TODO add post data as a Map or Object or...*/);
        int id = listingsDAO.save(listing);
        res.header("Location", String.format("/listings/%d", id));
        res.status(201);
        return "";
    }

    /**
     * @api {post} /listings/:id/book Book a listing
     * @apiName bookListing
     * @apiGroup Listings
     *
     * @apiDescription No body is returned.
     *
     * @apiParam {String} id Listing's ID
     * @apiParam {String[]} bookings List of date in format [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601). The time is not important, you should only consider the date.
     * @apiParamExample Request
     * POST /listings/:id/book
     * {
     *   "bookings": [ "2019-02-12T00:00:00Z"::string, ... ]
     * }
     *
     * @apiSuccessExample Response
     * 204 HTTP No Content
     */
    public static String bookListing(Request req, Response res) {
        // TODO
        return null;
    }
}