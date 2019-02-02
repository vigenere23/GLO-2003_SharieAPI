package com.github.glo2003.controllers;

import static com.github.glo2003.APIServer.listingsDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.models.Listing;

import spark.Request;
import spark.Response;

public class ListingsController {

  private static ObjectMapper jsonObjectMapper = new ObjectMapper();

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

  public static String addListing(Request req, Response res) {
    Listing listing = new Listing(/*TODO add post data as a Map or Object or...*/);
    int id = listingsDAO.save(listing);
    res.header("Location", String.format("/listings/%d", id));
    res.status(201);
    return "";
  }
}