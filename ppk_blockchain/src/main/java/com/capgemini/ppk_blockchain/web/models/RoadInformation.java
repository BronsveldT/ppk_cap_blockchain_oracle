package com.capgemini.ppk_blockchain.web.models;

/**
 * This model is used to process a single ride from the user. It is not to be used when returning information of the
 * car that is stored on the Blockchain Ledger.
 */
public class RoadInformation {
    public int carId;
    public double distanceToPrev;
    public double latitude;
    public int locationId;
    public double longitude;
    public int spits;
    public String street;
    public String town;
}
