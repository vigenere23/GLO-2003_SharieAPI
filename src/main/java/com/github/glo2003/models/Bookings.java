package com.github.glo2003.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bookings {
    private List<LocalDateTime> bookings;

    public Bookings() {
        bookings = new ArrayList<>();
    }

    public List<LocalDateTime> getBookings() { return bookings; }

    public void setBookings(List<LocalDateTime> bookings) {
        this.bookings = bookings;
    }
}
