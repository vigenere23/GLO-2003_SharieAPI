package com.github.glo2003;

import com.github.glo2003.helpers.ResponseHelper;
import com.github.glo2003.models.Listing;
import com.github.glo2003.models.Owner;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class ResponseHelperTest {

    private final String validOwnerName = "Test";
    private final String validOwnerPhoneNumber = "8191112222";
    private final String validOwnerEmail = "test@test.com";
    private final String validListingTitle = "New listing";
    private final String validListingDescription = "This is a new listing WOW";
    private Owner validOwner;
    private Listing validListing;

    @Before
    public void setupValidVariables() {
        validOwner = new Owner(validOwnerName, validOwnerPhoneNumber, validOwnerEmail);
        validListing = new Listing(validListingTitle, validListingDescription, validOwner);
    }

    @Test
    public void validJsonOwnerString_parseToOwner() throws IOException {
        String jsonOwner = "{\"name\":\"" + validOwnerName + "\",\"phoneNumber\":\"" + validOwnerPhoneNumber + "\",\"email\":\"" + validOwnerEmail + "\"}";
        Owner owner = ResponseHelper.isParameterValid(jsonOwner, Owner.class);
        assertThat(owner).isNotNull();
        assertThat(owner.getName()).isEqualTo(validOwnerName);
        assertThat(owner.getPhoneNumber()).isEqualTo(validOwnerPhoneNumber);
        assertThat(owner.getEmail()).isEqualTo(validOwnerEmail);
    }

    @Test
    public void validJsonListingString_parseToListing() throws IOException {
        String jsonOwner = "{\"name\":\"" + validOwnerName + "\",\"phoneNumber\":\"" + validOwnerPhoneNumber + "\",\"email\":\"" + validOwnerEmail + "\"}";
        String jsonListing = "{\"title\":\"" + validListingTitle + "\",\"description\":\"" + validListingDescription + "\",\"owner\":" + jsonOwner + "}";
        Listing listing = ResponseHelper.isParameterValid(jsonListing, Listing.class);
        assertThat(listing).isNotNull();
        assertThat(listing.getOwner()).isNotNull();
        assertThat(listing.getTitle()).isEqualTo(validListingTitle);
        assertThat(listing.getDescription()).isEqualTo(validListingDescription);
        assertThat(listing.getOwner().getName()).isEqualTo(validOwnerName);
        assertThat(listing.getOwner().getPhoneNumber()).isEqualTo(validOwnerPhoneNumber);
        assertThat(listing.getOwner().getEmail()).isEqualTo(validOwnerEmail);
    }
}
