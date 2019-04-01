package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.models.Listing;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Key;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MorphiaListingsDAO implements ListingsDAO {

    private Datastore datastore;

    public MorphiaListingsDAO() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.github.glo2003.models");
        String databaseUrl = Optional.ofNullable(System.getenv("SHARIE_DATABASE_URL")).orElse("localhost:9090");
        String databaseName = Optional.ofNullable(System.getenv("SHARIE_DATABASE_NAME")).orElse("localDB");

        MongoClientURI host = new MongoClientURI(databaseUrl);
        MongoClient mongoClient = new MongoClient(host);
        datastore = morphia.createDatastore(mongoClient, databaseName);
    }

    @Override
    public Listing get(String id) throws ItemNotFoundException {
        // TODO Revoir comment faire un get plus efficace
        try {
            Listing listing = datastore.get(Listing.class, new ObjectId(id));
            if (listing == null) {
                throw new ItemNotFoundException(String.format("No listing with id '%s' was found", id));
            }

            return listing;
        }
        catch (Exception e) {
            throw new ItemNotFoundException(String.format("No listing with id '%s' was found", id));
        }
    }

    @Override
    public List<Listing> getAll() {
        Query<Listing> query = datastore.createQuery(Listing.class);
        return query.asList();
    }

    @Override
    public List<Listing> getAllWithTitle(String title) {
        /*
        ArrayList<Listing> listOfListings = new ArrayList<>();
        for (Listing listing: getAll()) {
            for (String listingTitle: listing.getTitle()) {

            }
        }
        return listOfListings;
        */
        return null;
    }
    
    @Override
    public List<Listing> getAllSpecificDate(LocalDate date) {
        List<Listing> filteredListings = new ArrayList<>();
        for (Listing listing: getAll()) {
            for (Instant availability:listing.getAvailabilities()) {
                if(availability.atZone(ZoneOffset.UTC).getYear() == date.getYear()
                        && availability.atZone(ZoneOffset.UTC).getMonthValue() == date.getMonthValue()
                        && availability.atZone(ZoneOffset.UTC).getDayOfMonth() == date.getDayOfMonth()){
                    filteredListings.add(listing);
                }
            }
        }
        return filteredListings;
    }

    @Override
    public String save(Listing listing){
        Key<Listing> result = datastore.save(listing);
        return result.getId().toString();
    }

    @Override
    public void reset() {
        Query<Listing> query = datastore.createQuery(Listing.class);
        datastore.delete(query);
    }
}
