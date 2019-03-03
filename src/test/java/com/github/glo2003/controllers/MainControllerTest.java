package com.github.glo2003.controllers;

import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import spark.servlet.SparkApplication;
/*https://github.com/despegar/spark-test*/

public class MainControllerTest {
    public static class MainControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            new MainController();
        }
    }

    @ClassRule
    public static SparkServer<MainControllerTestSparkApplication> testServer = new SparkServer<>(MainControllerTestSparkApplication.class, 9090);
    /*
    TODO This test can't run along with other Controller's tests (like ListingsController)...
    TODO throws "com.github.glo2003.controllers.MainControllerTest: This must be done before route mapping has begun"
    @Test
    public void givenNewServer_GETPing_shouldreturnPong() throws HttpClientException {
        GetMethod getMethod = testServer.get("/ping", false);
        HttpResponse httpResponse = testServer.execute(getMethod);
        com.google.common.truth.Truth.assertThat(new String(httpResponse.body())).isEqualTo("pong");
    }*/
}
