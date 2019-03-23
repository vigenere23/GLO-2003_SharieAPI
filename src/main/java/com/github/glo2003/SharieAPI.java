package com.github.glo2003;

import com.github.glo2003.daos.InMemoryListingsDAO;

public class SharieAPI {
    public static void main(String ...args) {
        APIServer apiServer = new APIServer(new InMemoryListingsDAO());
        apiServer.run();
    }
}
