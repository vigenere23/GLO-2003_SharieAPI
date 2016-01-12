package com.github.glo2003;

import static spark.Spark.*;

import javaslang.control.Try;

public class HelloWorld {
    public static void main(String[] args) {

        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT")))
                                .orElse(1357);

        port(portNumber);

        get("/", (req,
                  res) -> "Project dashboard api");
    }
}
