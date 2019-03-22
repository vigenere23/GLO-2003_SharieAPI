package com.github.glo2003.controllers;

import static spark.Spark.*;

public class MainController implements Controller {
    public MainController() {
        setupRoutes();
    }

    public void setupRoutes() {
        get("/", (req, res) -> "Sharie API");
        get("/ping", (req, res) -> "pong");
    }
}
