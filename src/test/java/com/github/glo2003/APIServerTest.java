package com.github.glo2003;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import spark.servlet.SparkApplication;
/*https://github.com/despegar/spark-test*/

public class APIServerTest {
    public static class APIServerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            Object server = new APIServer();
            ((APIServer) server).main(null);
        }
    }

    @ClassRule
    public static SparkServer<APIServerTestSparkApplication> testServer = new SparkServer<>(APIServerTest.APIServerTestSparkApplication.class, 9090);

    @Test
    public void ListingsDetailsShouldReturn404IfIdDontExist() throws Exception {
        GetMethod get = testServer.get("/listings/100000", false);
        HttpResponse httpResponse = testServer.execute(get);
        assertEquals(404, httpResponse.code());
    }
}


