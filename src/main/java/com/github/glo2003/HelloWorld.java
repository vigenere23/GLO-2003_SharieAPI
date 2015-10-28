package com.github.glo2003;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        port(Integer.valueOf(System.getenv("PORT")));
      
        get("/hello", (req, res) -> "Hello World");

        exception(NotFoundException.class, (e, req, res) -> {
            res.status(404);
            res.body("Resource not found");
        });
        
    }
}
