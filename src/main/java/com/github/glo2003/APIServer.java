package com.github.glo2003;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.handlers.ScheduleHandler;

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
            System.err.println("There was an error retrieving PORT env var using the default one (1357)");
            return 1357;
        });

        port(portNumber);

        get("/", (req, res) -> "Project dashboard api");

        get("/ping", (req, res) -> "pong");

        get("/schedules/:startOfWeek", new ScheduleHandler(), jsonObjectMapper::writeValueAsString);

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
    }
}
