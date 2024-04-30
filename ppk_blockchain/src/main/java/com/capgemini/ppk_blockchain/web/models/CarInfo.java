package com.capgemini.ppk_blockchain.web.models;

import java.util.ArrayList;

/**
 * This model is used to process a single ride from the user. It is not to be used when returning information of the
 * car that is stored on the Blockchain Ledger.
 */
public class CarInfo {
    public String brandstoftype;
    public String emissieType;
    public String kenteken;
    public String merk;
    public ArrayList<RoadInformation> roadInformation;
}
