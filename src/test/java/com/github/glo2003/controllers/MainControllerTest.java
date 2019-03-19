package com.github.glo2003.controllers;

import com.github.glo2003.FunctionnalTest;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class MainControllerTest extends FunctionnalTest {

    @Test
    public void GET_index_shouldReturn200() {
        get().then()
            .statusCode(200);
    }

    @Test
    public void GET_ping_shouldReturn200pong() {
        get("/ping").then()
            .statusCode(200)
            .body(equalTo("pong"));
    }

}
