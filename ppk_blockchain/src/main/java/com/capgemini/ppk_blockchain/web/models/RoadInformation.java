package com.capgemini.ppk_blockchain.web.models;

/**
 * This model is used to process a single ride from the user. It is not to be used when returning information of the
 * car that is stored on the Blockchain Ledger.
 */
public class RoadInformation {
    private int carId;
    private double distanceToPrev;
    private double latitude;
    private int locationId;
    private double longitude;
    private int spits;
    private String street;
    private String town;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public double getDistanceToPrev() {
        return distanceToPrev;
    }

    public void setDistanceToPrev(double distanceToPrev) {
        this.distanceToPrev = distanceToPrev;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSpits() {
        return spits;
    }

    public void setSpits(int spits) {
        this.spits = spits;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "RoadInformation{" +
                "carId=" + carId +
                ", distanceToPrev=" + distanceToPrev +
                ", latitude=" + latitude +
                ", locationId=" + locationId +
                ", longitude=" + longitude +
                ", spits=" + spits +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}
