package com.github.glo2003;

import com.github.glo2003.daos.ListingsDAO;
import com.github.glo2003.models.Listing;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class ListingsDaoTest {

    private ListingsDAO listingsDAO;
    private Listing emptyListing;

    @Before
    public void setup() {
        listingsDAO = new ListingsDAO();
        emptyListing = new Listing();
    }

    @Test
    public void givenNewListingsDao_shouldBeEmpty() {
        assertThat(listingsDAO.getAll()).isEmpty();
    }

    @Test
    public void givenNewListingsDao_addNewListing_shouldContainsExactlyOneListing() {
        listingsDAO.save(emptyListing);
        assertThat(listingsDAO.getAll()).hasSize(1);
    }

    @Test
    public void givenAddNewListing_getListingById_shouldNotReturnNull() {
        long id = listingsDAO.save(emptyListing);
        Listing listing = listingsDAO.get(id);
        assertThat(listing).isNotNull();
    }

    @Test
    public void givenAddNewListing_getListingById_shouldReturnAListing() {
        long id = listingsDAO.save(new Listing());
        Listing listing = listingsDAO.get(id);
        assertThat(listing).isInstanceOf(Listing.class);
    }

    @Test
    public void givenAddNewListing_getListingById_shouldReturnTheSameListing() {
        long id = listingsDAO.save(emptyListing);
        Listing listing = listingsDAO.get(id);
        assertThat(listing).isEqualTo(emptyListing);
    }

    @Test
    public void givenNewListingsDao_getListing_shouldReturnNull() {
        assertThat(listingsDAO.get(0)).isNull();
    }
}
