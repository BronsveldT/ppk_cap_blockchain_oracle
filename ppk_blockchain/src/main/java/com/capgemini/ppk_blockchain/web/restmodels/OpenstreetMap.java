package com.capgemini.ppk_blockchain.web.restmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class OpenstreetMap {
    private int place_id;
    private String licence;
    private String osm_type;
    private int osm_id;
    private String lat;
    private String lon;
    @JsonProperty("class")
    private String myclass;
    private String type;
    private int place_rank;
    private double importance;
    private String addresstype;
    private String name;
    private String display_name;
    private Address address;
    private ArrayList<String> boundingbox;

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOsm_type() {
        return osm_type;
    }

    public void setOsm_type(String osm_type) {
        this.osm_type = osm_type;
    }

    public int getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(int osm_id) {
        this.osm_id = osm_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlace_rank() {
        return place_rank;
    }

    public void setPlace_rank(int place_rank) {
        this.place_rank = place_rank;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<String> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(ArrayList<String> boundingbox) {
        this.boundingbox = boundingbox;
    }

    @Override
    public String toString() {
        return "OpenstreetMap{" +
                "place_id=" + place_id +
                ", licence='" + licence + '\'' +
                ", osm_type='" + osm_type + '\'' +
                ", osm_id=" + osm_id +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", myclass='" + myclass + '\'' +
                ", type='" + type + '\'' +
                ", place_rank=" + place_rank +
                ", importance=" + importance +
                ", addresstype='" + addresstype + '\'' +
                ", name='" + name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", address=" + address +
                ", boundingbox=" + boundingbox +
                '}';
    }
}
