package com.github.glo2003;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.handlers.PizzaMenuHandler;

import javaslang.control.Option;
import javaslang.control.Try;

public class PizzeriaAPIServer
{
    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public PizzeriaAPIServer()
    {

    }

    public static void main(String[] args)
    {
        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
            System.err.println("There was an error retrieving PORT env var using the default one (1357)");
            return 1357;
        });

        String githubToken = Option.of(System.getenv("GITHUB_TOKEN")).orElse("");
        if (githubToken.isEmpty()) {
            System.err.println("You need to pass the GITHUB_TOKEN env var to query private repositories");
        }

        port(portNumber);

        get("/", (req,
                  res) -> "Project dashboard api");

        get("/ping", (req,
                      res) -> "pong");

        get("/menu/pizzas", new PizzaMenuHandler(), jsonObjectMapper::writeValueAsString);

        options("*", (request,
                      response) -> "");
    }

}
