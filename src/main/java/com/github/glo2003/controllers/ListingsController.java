package com.github.glo2003.controllers;

import static com.github.glo2003.APIServer.listingsDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.models.Listing;

import spark.Request;
import spark.Response;

public class ListingsController {

  private static ObjectMapper jsonObjectMapper = new ObjectMapper();

  /**
   * @api {get} /listings/:id Get single listing
   * @apiName getListing
   * @apiGroup Listings
   * 
   * @apiParam {String} id Listing's ID
   */
  public static String getListing(Request req, Response res) {
    int id = 0;
    try {
      String stringId = req.params(":id");
      id = Integer.parseInt(stringId);
      
    } catch (Exception e) {
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
   * @api {post} /listings Add a new listing
   * @apiName addListing
   * @apiGroup Listings
   * 
   * @apiDescription No body is returned, only the header `Location`.
   * 
   * @apiParam {String} title Listing's title.
   * @apiParam {String} description Listing's description.
   * @apiParam {Object} owner Listing's owner
   * @apiParam {String} owner.name Owner's name.
   * @apiParam {String} owner.phoneNumber Owner's phone number.
   * @apiParam {String} owner.email Owner's email.
   * 
   * @apiParamExample Params example:
   *    {
   *      title: "Fresh new Honda Civic ONLY 3000$",
   *      description: "Just buy it, i'll be worth it!",
   *      owner: {
   *        name: "Frank Desjose",
   *        phoneNumber: "123 456-7890",
   *        email: "frankito.deslato@myman.com"
   *      }
   *    }
   * 
   * @apiSuccess (Created 201) {Number} id ID of the newly created listing
   * @apiSuccessExample Success Example:
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
}