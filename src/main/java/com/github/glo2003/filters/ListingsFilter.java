package com.github.glo2003.filters;

import com.github.glo2003.models.Listing;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ListingsFilter {

    public static List<Listing> filterByDate(List<Listing> listings, LocalDate date) {
        List<Listing> filteredListings = new ArrayList<>();

        for (Listing listing : listings) {
            for (Instant availability:listing.getAvailabilities()) {
                if (
                    availability.atZone(ZoneOffset.systemDefault()).getYear() == date.getYear() &&
                    availability.atZone(ZoneOffset.systemDefault()).getMonthValue() == date.getMonthValue() &&
                    availability.atZone(ZoneOffset.systemDefault()).getDayOfMonth() == date.getDayOfMonth()
                ) {
                    filteredListings.add(listing);
                }
            }
        }

        return filteredListings;
    }

    public static List<Listing> filterByTitle(List<Listing> listings, String title) {
        List<Listing> filteredListings = new ArrayList<>();

        String[] searchTerms = title.split(" ");

        for (Listing listing : listings) {
            Boolean listingMatches = true;

            for (String searchTerm : searchTerms) {
                Boolean searchTermMatches = false;
                for (String word : listing.getTitle().split(" ")) {
                    if (word.toLowerCase().contains(searchTerm.toLowerCase())) {
                        searchTermMatches = true;
                        break;
                    }
                }
                if (!searchTermMatches) {
                    listingMatches = false;
                    break;
                }
            }

            if (listingMatches) filteredListings.add(listing);
        }

        return filteredListings;
    }

}