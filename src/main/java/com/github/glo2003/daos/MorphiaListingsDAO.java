package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.models.Listing;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MorphiaListingsDAO implements ListingsDAO {

    private Datastore datastore;

    public MorphiaListingsDAO() {
        //TODO connect to database
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.github.glo2003.models");
        String databaseUrl = Optional.ofNullable(System.getenv("DATABASE_URL")).orElse("localhost:9090");
        String databaseName = Optional.ofNullable(System.getenv("DATABASE_NAME")).orElse("localDB");

        MongoClientURI host = new MongoClientURI(databaseUrl);
        try {
            MongoClient mongoClient = new MongoClient(host);
            datastore = morphia.createDatastore(mongoClient, databaseName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Listing get(long id) throws ItemNotFoundException {
        //Revoir comment faire un get plus efficace
        Query<Listing> query = datastore.createQuery(Listing.class);
        return query.asList().get((int) id);
    }

    @Override
    public List<Listing> getAll() {
        Query<Listing> query = datastore.createQuery(Listing.class);
        return query.asList();
    }

    @Override
    public List<Listing> getAllSpecificDate(LocalDate date) {
        return null;
    }

    @Override
    public long save(Listing listing) throws ItemAlreadyExistsException {
        datastore.save(listing);
        //Revoir le retour pour que ca retourne le id
        return 1;
    }
}
