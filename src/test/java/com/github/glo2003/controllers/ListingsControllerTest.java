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

    private final String validListingsPost =
        "{" +
            "\"title\":\"New listing\"," +
            "\"description\":\"Just a new listing\"," +
            "\"owner\":{" +
                "\"name\":\"John Smith\"," +
                "\"phoneNumber\":\"8191112222\"," +
                "\"email\":\"test@test.com\"" +
            "}" +
        "}";
    private final String validListingsPost2 =
        "{" +
            "\"title\":\"Another listing\"," +
            "\"description\":\"Just another listing\"," +
            "\"owner\":{" +
                "\"name\":\"Jane Smith\"," +
                "\"phoneNumber\":\"8192223333\"," +
                "\"email\":\"name@email.com\"" +
            "}" +
        "}";
    private final String jsonListingRegexFormatWithNumberOfAvailabilities = "\\{\"title\":\"[\\w\\s-]*\",\"description\":\"[\\w\\s-]*\",\"availabilities\":\\[(,?\"[0-9]{4}-[0-9]{2}-[0-9]{2}T00:00:00Z\"){%d}\\],\"owner\":\\{\"name\":\"[\\w\\s-]*\",\"phoneNumber\":\"[0-9]*\",\"email\":\"[\\w\\s@\\.-]*\"\\}\\}";
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
    public void givenNewServer_POSTValidListing_shouldReturnEmptyBody() throws HttpClientException {
        HttpResponse httpResponse = postListing(validListingsPost);
        assertThat(new String(httpResponse.body())).isEmpty();
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
    public void givenPostValidListing_GETSingleListing_shouldReturnJsonMatchingRegexp() throws HttpClientException {
        HttpResponse httpResponse = postListing(validListingsPost);
        String[] SlashSplittedHeaderLocation = httpResponse.headers().get("Location").get(0).split("/");
        String id = SlashSplittedHeaderLocation[SlashSplittedHeaderLocation.length-1];

        HttpResponse httpGetResponse = getSingleListing(id);
        String jsonListingWith7Availabilities = String.format(jsonListingRegexFormatWithNumberOfAvailabilities, 7);
        assertThat(new String(httpGetResponse.body())).matches("^" + jsonListingWith7Availabilities + "$");
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
    public void givenPOSTTwoValidListings_GETAllListings_shouldReturnListWithTwoListings() throws HttpClientException {
        postListing(validListingsPost);
        postListing(validListingsPost2);

        HttpResponse httpResponse = getAllListings();
        String jsonListingWith7Availabilities = String.format(jsonListingRegexFormatWithNumberOfAvailabilities, 7);
        String expectedResponseBodyRegexp = String.format("\\{\"listings\":\\[(,?%s){2}\\]\\}", jsonListingWith7Availabilities);
        assertThat(new String(httpResponse.body())).matches("^" + expectedResponseBodyRegexp + "$");
    }
}
