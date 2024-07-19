package com.capgemini.ppk_blockchain.web.restmodels;

import lombok.ToString;

import java.util.ArrayList;

/**
 * This model is used to process a single ride from the user. It is not to be used when returning information of the
 * car that is stored on the Blockchain Ledger.
 */
@ToString
public class CarInfo {
    private String brandstoftype;
    private String emissieType;
    private String kenteken;
    private String merk;
    private ArrayList<RoadInformation> roadInformation;

    public String getBrandstoftype() {
        return brandstoftype;
    }

    public String getEmissieType() {
        return emissieType;
    }

    public String getKenteken() {
        return kenteken;
    }

    public String getMerk() {
        return merk;
    }

    public ArrayList<RoadInformation> getRoadInformation() {
        return roadInformation;
    }
}
