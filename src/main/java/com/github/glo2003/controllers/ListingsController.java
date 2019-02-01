package com.github.glo2003.controllers;

import static com.github.glo2003.APIServer.listingsDAO;

import com.github.glo2003.models.Listing;

import spark.Request;
import spark.Response;
import spark.Route;

public class ListingsController {

  public static Listing getListing(Request req, Response res) {
    int id = 0;
    try {
      String stringId = req.params(":id");
      id = Integer.parseInt(stringId);
      
    } catch (Exception e) {
      //TODO: handle exception
    }
    
    // TODO send listingsDAO.get(id) in a json format with correct flags
    return listingsDAO.get(id);
  }

  /*TODO change to void and use res.send instead?*/
  public static int addListing(Request req, Response res) {
    Listing listing = new Listing(/*TODO add post data as a Map or Object or...*/);
    return listingsDAO.save(listing);
    // TODO send listingsDAO.save(listing) in a json format with corretc flags
  }
}