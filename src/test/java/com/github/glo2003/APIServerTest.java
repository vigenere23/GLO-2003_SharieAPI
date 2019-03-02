package com.github.glo2003;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;
import static com.google.common.truth.Truth.*;
/*https://github.com/despegar/spark-test*/

public class APIServerTest {
    public static class APIServerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            APIServer.main(null);
        }
    }

    @ClassRule
    public static SparkServer<APIServerTestSparkApplication> testServer = new SparkServer<>(APIServerTest.APIServerTestSparkApplication.class, 9090);

    @Test
    public void ListingsDetailsShouldReturn404IfIdDontExist() throws Exception {
        GetMethod get = testServer.get("/listings/100000", false);
        HttpResponse httpResponse = testServer.execute(get);
        System.out.println("RETURN CODE : " + httpResponse.code());
        assertThat(httpResponse.code()).isEqualTo(404);
    }
}


