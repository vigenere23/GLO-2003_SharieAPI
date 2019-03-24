package com.github.glo2003.stubs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookingsPostDTO {
    private List<String> bookings;

    public BookingsPostDTO(List<Instant> bookings) {
        setBookings(bookings);
    }

    public List<String> getBookings() { return bookings; }

    public void setBookings(List<Instant> bookings) {
        this.bookings = new ArrayList<>();
        for (Instant instant : bookings) {
            this.bookings.add(instant.toString());
        }
    }
}
