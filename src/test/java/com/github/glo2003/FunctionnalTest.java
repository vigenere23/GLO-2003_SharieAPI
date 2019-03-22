package com.github.glo2003;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class FunctionnalTest {

    @BeforeClass
    public static void launchServer() {
        ServerSetup.launchServer();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9090;
    }

}
