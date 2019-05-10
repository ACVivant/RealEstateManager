package com.openclassrooms.realestatemanager.models;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */

@Entity
public class Address {

    @PrimaryKey(autoGenerate = true)
    private long addressId;
    private String numberInStreet;
    private String street;
    private String street2;
    private String zipcode;
    private String town;
    private String country;

    public Address() {}

    public Address(String number, String street, String street2, String zipcode, String town, String country) {
        this.numberInStreet = number;
        this.street = street;
        this.street2 = street2;
        this.zipcode = zipcode;
        this.town = town;
        this.country = country;
    }

    // --- GETTER ---
    public long getAddressId() {
        return addressId;
    }
    public String getNumberInStreet() {
        return numberInStreet;
    }
    public String getStreet() {
        return street;
    }
    public String getStreet2() {
        return street2;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getTown() {
        return town;
    }
    public String getCountry() {
        return country;
    }


    // --- SETTER ---
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
    public void setNumberInStreet(String numberInStreet) {
        this.numberInStreet = numberInStreet;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}


