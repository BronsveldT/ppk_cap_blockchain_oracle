package com.capgemini.ppk_blockchain.web.restmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    private String road;
    private String town;
    private String municipality;
    private String state;
    @JsonProperty("ISO3166-2-lvl4")
    private String administrative;
    private String country;
    private String postcode;
    private String country_code;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdministrative() {
        return administrative;
    }

    public void setAdministrative(String administrative) {
        this.administrative = administrative;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    @Override
    public String toString() {
        return "Address{" +
                "road='" + road + '\'' +
                ", town='" + town + '\'' +
                ", municipality='" + municipality + '\'' +
                ", state='" + state + '\'' +
                ", administrative='" + administrative + '\'' +
                ", country='" + country + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country_code='" + country_code + '\'' +
                '}';
    }
}
