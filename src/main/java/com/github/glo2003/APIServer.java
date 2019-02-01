package com.github.glo2003;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import javaslang.control.Option;
import javaslang.control.Try;

import com.github.glo2003.controllers.*;
import com.github.glo2003.daos.*;

public class APIServer
{
    public static ListingsDAO listingsDAO;

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public APIServer()
    {
    }

    public static void main(String[] args)
    {
        listingsDAO = new ListingsDAO();

        setupPort();
        setupRoutes();
        enableCORS("*", "*", "*");
    }

    private static void setupPort() {
        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
            System.out.println("WARNING: The server port could not be found with 'PORT' env var. Using the default one (9090)");
            return 9090;
        });

        port(portNumber);
    }

    private static void setupRoutes() {
        get("/", (req, res) -> "Sharie API");
        get("/ping", (req, res) -> "pong");

        // TODO use path() to better separate routes
        //get("/listings", ListingsController.getAllListings);
        post("/listings", (req, res) -> ListingsController.addListing(req, res));
        get("/listings/:id", (req, res) -> ListingsController.getListing(req, res));
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.type("application/json");
        });
    }
}
