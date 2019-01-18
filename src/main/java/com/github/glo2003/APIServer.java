package com.github.glo2003;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import javaslang.control.Option;
import javaslang.control.Try;

public class APIServer
{
    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public APIServer()
    {
    }

    public static void main(String[] args)
    {
        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
            System.out.println("WARNING: The server port could not be found with 'PORT' env var. Using the default one (9090)");
            return 9090;
        });

        port(portNumber);

        get("/", (req, res) -> "Sharie API");

        get("/ping", (req, res) -> "pong");

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
    }
}
