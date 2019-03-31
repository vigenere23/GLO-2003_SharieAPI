package com.github.glo2003;

import com.github.glo2003.daos.InMemoryListingsDAO;
import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.daos.MorphiaListingsDAO;

import java.util.Optional;

public class SharieAPI {
    public static void main(String ...args) {
        String profile = Optional.ofNullable(System.getenv("SHARIE_PROFILE")).orElse("dev");
        ListingsDAO listingsDAO;

        if(profile.equals("dev")){
            listingsDAO = new InMemoryListingsDAO();
        }else if(profile.equals("prod") || profile.equals("test")){
            listingsDAO = new MorphiaListingsDAO();
        }
        else{
            throw new IllegalArgumentException("Unknown profile");
        }

        APIServer apiServer = new APIServer(listingsDAO);
        apiServer.run();
    }
}
