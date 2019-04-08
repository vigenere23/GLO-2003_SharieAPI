package com.github.glo2003.filters;

import com.github.glo2003.models.Listing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ListingsFilter {

    public static List<Listing> filterByDate(List<Listing> listings, Instant date) {
        List<Listing> filteredListings = new ArrayList<>();

        for (Listing listing : listings) {
            for (Instant availability : listing.getAvailabilities()) {
                if (availability.toEpochMilli() == date.toEpochMilli()) {
                    filteredListings.add(listing);
                }
            }
        }

        return filteredListings;
    }

    public static List<Listing> filterByTitle(List<Listing> listings, String title) {
        List<Listing> filteredListings = new ArrayList<>();

        for (Listing listing : listings) {
            if (searchMatchesText(title, listing.getTitle())) {
                filteredListings.add(listing);
            }
        }

        return filteredListings;
    }

    public static List<Listing> filterByCategory(List<Listing> listings, String category) {
        List<Listing> filteredListings = new ArrayList<>();

        for (Listing listing : listings) {
            if (listing.getCategory().name().toLowerCase().equals(category.toLowerCase())) {
                filteredListings.add(listing);
            }
        }

        return filteredListings;
    }

    private static Boolean searchMatchesText(String search, String text) {
        String[] words = text.split(" ");
        for (String searchTerm : search.split(" ")) {
            Boolean searchTermMatches = false;
            for (String word : words) {
                if (word.toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchTermMatches = true;
                    break;
                }
            }
            if (!searchTermMatches) {
                return false;
            }
        }
        return true;
    }

}