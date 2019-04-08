package com.github.glo2003.models;

import com.github.glo2003.exceptions.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ListingTest {
    private Listing emptyListing;

    @Before
    public void resetVariables() {
        emptyListing = new Listing();
    }

    @Test
    public void givenNewListing_getAvailabilities_shouldReturn7Items() {
        assertThat(emptyListing.getAvailabilities().size()).isEqualTo(7);
    }

    @Test
    public void givenNewListing_getAvailabilities_shouldReturnListOfTypeInstants() {
        assertThat(emptyListing.getAvailabilities().get(0)).isInstanceOf(Instant.class);
    }

    @Test
    public void givenNewListing_getAvailabilities_shouldReturnFirstItemAsToday() {
        Instant firstInstantOfList = emptyListing.getAvailabilities().get(0);
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        assertThat(firstInstantOfList.toString()).isEqualTo(today.toString());
    }

    @Test
    public void givenNewListing_bookSingleDate_shouldRemoveThatDate() throws ItemNotFoundException {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        List<Instant> bookings = new ArrayList<>();
        bookings.add(today);
        emptyListing.book(bookings);
        assertThat(emptyListing.getAvailabilities()).doesNotContain(today);
    }

    @Test
    public void givenNewListing_bookMultipleDates_shouldRemoveAllThoseDates() throws ItemNotFoundException {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant tomorrow = today.plus(1, ChronoUnit.DAYS);
        List<Instant> bookings = new ArrayList<>();
        bookings.add(today);
        bookings.add(tomorrow);
        emptyListing.book(bookings);
        assertThat(emptyListing.getAvailabilities()).containsNoneOf(today, tomorrow);
    }

    @Test(expected = ItemNotFoundException.class)
    public void givenNewListing_bookSingleListingNotAvailable_shouldThrowItemNotFoundException() throws ItemNotFoundException {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant yesterday = today.minus(1, ChronoUnit.DAYS);
        List<Instant> bookings = new ArrayList<>();
        bookings.add(yesterday);
        emptyListing.book(bookings);
    }

    @Test(expected = ItemNotFoundException.class)
    public void givenNewListing_bookMultipleListingWhichOneIsNotAvailable_shouldThrowItemNotFoundException() throws ItemNotFoundException {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant yesterday = today.minus(1, ChronoUnit.DAYS);
        List<Instant> bookings = new ArrayList<>();
        bookings.add(today);
        bookings.add(yesterday);
        emptyListing.book(bookings);
    }

    @Test(expected = ItemNotFoundException.class)
    public void givenNewListing_bookSameListingTwice_shouldThrowItemNotFoundException() throws ItemNotFoundException {
        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
        List<Instant> bookings = new ArrayList<>();
        bookings.add(today);
        emptyListing.book(bookings);
        emptyListing.book(bookings);
    }

    @Test
    public void givenNewListing_addRating_shouldReturnRatingListWithOneValue() {
        List<Rating> ratings = emptyListing.getRatings();
        assertThat(ratings.size()).isEqualTo(0);
    }
}
