package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.models.Listing;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public class ListingsDaosTest {

    private ListingsDAO listingsDAO;
    private Listing validListing;
    private Listing validListing2;

    @Before
    public void setupBefore() {
        String profile = Optional.ofNullable(System.getenv("SHARIE_PROFILE")).orElse("dev");
        ListingsDAO listingsDAO;

        if(profile.equals("dev")){
            listingsDAO = new InMemoryListingsDAO();
        }else if(profile.equals("test")){
            listingsDAO = new MorphiaListingsDAO();
        }
        else{
            throw new IllegalArgumentException("Unknown profile");
        }

        listingsDAO.reset();

        validListing = new Listing();
        validListing2 = new Listing();
    }

    @Test
    public void givenNewListingsDao_getAllListings_shouldReturnEmptyList() {
        assertThat(listingsDAO.getAll()).isEmpty();
    }

    @Test
    public void givenAddedOneListing_getAllListings_shouldReturnListOfSizeOne() throws ItemAlreadyExistsException {
        listingsDAO.save(validListing);
        assertThat(listingsDAO.getAll()).hasSize(1);
    }

    @Test
    public void givenAddedTwoListings_getAllListings_shouldReturnListOfSizeTwo() throws ItemAlreadyExistsException {
        listingsDAO.save(validListing);
        listingsDAO.save(validListing2);
        assertThat(listingsDAO.getAll()).hasSize(2);
    }

    @Test(expected = ItemAlreadyExistsException.class)
    public void givenListingAlreadyAdded_addSameListing_shouldThrowItemAlreadyExistsException() throws ItemAlreadyExistsException {
        listingsDAO.save(validListing);
        listingsDAO.save(validListing);
    }

    @Test(expected = ItemNotFoundException.class)
    public void givenNewListingsDao_getAnyListing_shouldThrowItemNotFoundException() throws ItemNotFoundException {
        listingsDAO.get("TEsting");
    }

    @Test
    public void givenNewListingsDao_addListing_shouldReturnNotNull() throws ItemAlreadyExistsException {
        assertThat(listingsDAO.save(validListing)).isNotNull();
    }

    @Test
    public void givenAddedOneListing_getListing_shouldReturnNotNull() throws ItemNotFoundException, ItemAlreadyExistsException {
        String id = listingsDAO.save(validListing);
        assertThat(listingsDAO.get(id)).isNotNull();
    }

    @Test
    public void givenAddedOneListing_getListing_shouldReturnSameListing() throws ItemNotFoundException, ItemAlreadyExistsException {
        String id = listingsDAO.save(validListing);
        assertThat(listingsDAO.get(id)).isEqualTo(validListing);
    }
}
