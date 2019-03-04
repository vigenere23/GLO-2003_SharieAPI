package com.github.glo2003.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Bookings {
    private List<Instant> bookings;

    public Bookings() {
        bookings = new ArrayList<>();
    }

    public List<Instant> getBookings() { return bookings; }

    public void setBookings(List<String> bookings) {
        for (String booking : bookings) {
            this.bookings.add(Instant.parse(booking));
        }
    }
}
