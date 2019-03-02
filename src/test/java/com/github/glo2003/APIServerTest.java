package com.github.glo2003;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import static com.google.common.truth.Truth.assertThat;
/*https://github.com/despegar/spark-test*/

public class APIServerTest {

    private final String validListingsPost = "{\"title\":\"New listing\",\"description\":\"Just a new listing\",\"owner\":{\"name\":\"John Smith\",\"phoneNumber\":\"8191112222\",\"email\":\"test@test.com\"}}";

    public static class APIServerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            APIServer.main(null);
        }
    }

    @ClassRule
    public static SparkServer<APIServerTestSparkApplication> testServer = new SparkServer<>(APIServerTestSparkApplication.class, 9090);

    @Test
    public void givenNewServer_POSTValidListing_shouldReturnStatus201() throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", validListingsPost, false);
        HttpResponse httpResponse = testServer.execute(postListing);
        assertThat(httpResponse.code()).isEqualTo(201);
    }

    @Test
    public void givenNewServer_POSTListingWithValidBody_shouldReturnCorrectHeaderLocation() throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", validListingsPost, false);
        HttpResponse httpResponse = testServer.execute(postListing);
        assertThat(httpResponse.headers()).containsKey("Location");
        assertThat(httpResponse.headers().get("Location").get(0)).matches("^/listings/[0-9]*$");
    }

    @Test
    public void givenNewServer_POSTListingWithInvalidArguments_shouldReturnStatus400() throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", "", false);
        HttpResponse httpResponse = testServer.execute(postListing);
        assertThat(httpResponse.code()).isEqualTo(400);
    }

    @Test
    public void givenNewServer_POSTListingWithInvalidJson_shouldReturnStatus400() throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", "{", false);
        HttpResponse httpResponse = testServer.execute(postListing);
        assertThat(httpResponse.code()).isEqualTo(400);
    }

    @Test
    public void givenNewServer_GETAnySingleListing_shouldReturnStatus404() throws HttpClientException {
        GetMethod getListing = testServer.get("/listings/100000", false);
        HttpResponse httpResponse = testServer.execute(getListing);
        assertThat(httpResponse.code()).isEqualTo(404);
    }

    @Test
    public void givenPostValidListing_GETListingWithReturnedId_shouldReturnSameJsonAsBody() throws HttpClientException {
        PostMethod postListing = testServer.post("/listings", validListingsPost, false);
        HttpResponse httpPostResponse = testServer.execute(postListing);
        String[] SlashSplittedHeaderLocation = httpPostResponse.headers().get("Location").get(0).split("/");
        String id = SlashSplittedHeaderLocation[SlashSplittedHeaderLocation.length-1];

        GetMethod getListing = testServer.get("/listings/" + id, false);
        HttpResponse httpGetResponse = testServer.execute(getListing);
        assertThat(new String(httpGetResponse.body())).isEqualTo(validListingsPost);
    }
}
