package com.github.glo2003.controllers;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import static com.google.common.truth.Truth.assertThat;
/*https://github.com/despegar/spark-test*/

public class ListingsControllerTest {

    private final String validListingsPost = "{\"title\":\"New listing\",\"description\":\"Just a new listing\",\"owner\":{\"name\":\"John Smith\",\"phoneNumber\":\"8191112222\",\"email\":\"test@test.com\"}}";
    private final String validListingsPost2 = "{\"title\":\"Another listing\",\"description\":\"Just another listing\",\"owner\":{\"name\":\"Mary Smith\",\"phoneNumber\":\"4186669999\",\"email\":\"name@email.com\"}}";

    public static class ListingsControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            new ListingsController();
        }
    }

    @ClassRule
    public static SparkServer<ListingsControllerTestSparkApplication> testServer = new SparkServer<>(ListingsControllerTestSparkApplication.class, 9090);

    @AfterClass
    public static void stopServer() {

    }

    @Before
    public void resetListingsDao() {
        testServer.getApplication().init();
    }

    private HttpResponse getSingleListing(String id) throws HttpClientException {
        GetMethod getListing = testServer.get("/listings/" + id, false);
        return testServer.execute(getListing);
    }

    private HttpResponse getSingleListing(long id) throws HttpClientException {
        GetMethod getListing = testServer.get("/listings/" + id, false);
        return testServer.execute(getListing);
    }

    private HttpResponse getAllListings() throws HttpClientException {
        GetMethod getAllListings = testServer.get("/listings", false);
        return testServer.execute(getAllListings);
    }

    private HttpResponse postListing(String body) throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", body, false);
        return testServer.execute(postListing);
    }

    @Test
    public void givenNewServer_POSTValidListing_shouldReturnStatus201() throws HttpClientException {
        HttpResponse httpResponse = postListing(validListingsPost);
        assertThat(httpResponse.code()).isEqualTo(201);
    }

    @Test
    public void givenNewServer_POSTListingWithValidBody_shouldReturnCorrectHeaderLocation() throws HttpClientException {
        HttpResponse httpResponse = postListing(validListingsPost);
        assertThat(httpResponse.headers()).containsKey("Location");
        assertThat(httpResponse.headers().get("Location").get(0)).matches("^/listings/[0-9]*$");
    }

    @Test
    public void givenNewServer_POSTListingWithInvalidArguments_shouldReturnStatus400() throws HttpClientException {
        HttpResponse httpResponse = postListing("");
        assertThat(httpResponse.code()).isEqualTo(400);
    }

    @Test
    public void givenNewServer_POSTListingWithInvalidJson_shouldReturnStatus400() throws HttpClientException {
        HttpResponse httpResponse = postListing("{");
        assertThat(httpResponse.code()).isEqualTo(400);
    }

    @Test
    public void givenNewServer_GETAnySingleListing_shouldReturnStatus404() throws HttpClientException {
        HttpResponse httpResponse = getSingleListing(100000);
        assertThat(httpResponse.code()).isEqualTo(404);
    }

    @Test
    public void givenServerWithListing_GETSingleListingWithListingId_shouldReturnStatus200() throws HttpClientException {
        HttpResponse httpResponse1 = postListing(validListingsPost);
        String[] SlashSplittedHeaderLocation = httpResponse1.headers().get("Location").get(0).split("/");
        String id = SlashSplittedHeaderLocation[SlashSplittedHeaderLocation.length-1];

        HttpResponse httpResponse = getSingleListing(id);
        assertThat(httpResponse.code()).isEqualTo(200);
    }

    @Test
    public void givenPostValidListing_GETSingleListing_shouldReturnSameJsonAsBody() throws HttpClientException {
        HttpResponse httpResponse = postListing(validListingsPost);
        String[] SlashSplittedHeaderLocation = httpResponse.headers().get("Location").get(0).split("/");
        String id = SlashSplittedHeaderLocation[SlashSplittedHeaderLocation.length-1];

        HttpResponse httpGetResponse = getSingleListing(id);
        assertThat(new String(httpGetResponse.body())).isEqualTo(validListingsPost);
    }

    @Test
    public void givenNewServer_GETAllListings_shouldReturnStatus200() throws HttpClientException {
        HttpResponse httpResponse = getAllListings();
        assertThat(httpResponse.code()).isEqualTo(200);
    }

    @Test
    public void givenNewServer_GETAllListings_shouldReturnEmptyList() throws HttpClientException {
        HttpResponse httpResponse = getAllListings();
        String expectedResponseBody = "{\"listings\":[]}";
        assertThat(new String(httpResponse.body())).isEqualTo(expectedResponseBody);
    }

    @Test
    public void givenPOSTValidListings_GETAllListings_shouldReturnListWithAllPostedListing() throws HttpClientException {
        postListing(validListingsPost);
        postListing(validListingsPost2);

        HttpResponse httpResponse = getAllListings();
        String expectedResponseBody = String.format("{\"listings\":[%s,%s]}", validListingsPost, validListingsPost2);
        String expectedResponseBody2 = String.format("{\"listings\":[%s,%s]}", validListingsPost2, validListingsPost);
        assertThat(new String(httpResponse.body())).isAnyOf(expectedResponseBody, expectedResponseBody2);
    }
}
