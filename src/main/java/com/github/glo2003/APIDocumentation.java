package com.github.glo2003;

public class APIDocumentation {
    /**
     * @api {get} /listings/:id Get a single listing
     * @apiName getListing
     * @apiGroup Listings
     *
     * @apiParam {String} id Listing's ID
     *
     * @apiSuccess {String[]} availabilities List of date in format [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601). By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.
     * @apiSuccessExample Response
     * 200 HTTP OK
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
     *
     * @apiError ListingNotFound The listing with `id` was not found.
     * @apiErrorExample Listing not found
     * 404 HTTP NOT FOUND
     * {
     *     "error" : "No listing with id '1224521054' was not found"
     * }
     *
     * @apiError WrongParamters The given listing's id could not be parsed as `long` type
     * @apiErrorExample Wrong id
     * 400 HTTP BAD REQUEST
     * {
     *     "error": "Id '1234test5678' should be of type 'long'"
     * }
     */

    /**
     * @api {get} /listings Get all listings
     * @apiName getAllListings
     * @apiGroup Listings
     *
     * @apiSuccess {String[]} availabilities List of date in format [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601). By default, it should contain 7 dates, starting from today. The time is not important as it is ignored by the frontend.
     * @apiSuccessExample Response
     * 200 HTTP OK
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
     */

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
     *
     * @apiError WrongParameters The parameters sent to create a `Listing` don't match those of the listing's class or the body format is not valid JSON
     * @apiErrorExample Wrong parameters
     * 400 HTTP BAD REQUEST
     * {
     *     "error" : "Parameters are not valid for creating an object 'Listing'"
     * }
     */

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
     * 204 HTTP NO CONTENT
     */
}
