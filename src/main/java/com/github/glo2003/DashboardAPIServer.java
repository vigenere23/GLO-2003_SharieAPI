package com.github.glo2003;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.handlers.ProjectHandler;

import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Try;

public class DashboardAPIServer
{
    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public DashboardAPIServer()
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

        String rethinkURI = Option.of(System.getenv("RETHINKDB")).orElse("");
        if (rethinkURI.isEmpty()) {
            System.err.println("You need to pass the RETHINKDB env var to store data");
        }

        port(portNumber);

        get("/", (req,
                  res) -> "Project dashboard api");

        get("/ping", (req,
                      res) -> "pong");

        get("/projects", new ProjectHandler(), jsonObjectMapper::writeValueAsString);

        options("/", (request, response) -> "");

        before((req, resp) -> {
            resp.header("Access-Control-Allow-Origin", "*");
            resp.header("Access-Control-Request-Method", req.requestMethod());
            resp.header("Access-Control-Allow-Headers",
                        List.ofAll(req.headers()).reduceLeft((s,l) -> (s.isEmpty() ? "": s + ", ")+ l));
        });
    }

}
