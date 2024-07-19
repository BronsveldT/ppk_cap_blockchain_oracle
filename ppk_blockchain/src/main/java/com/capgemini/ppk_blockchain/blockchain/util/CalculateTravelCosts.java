package com.capgemini.ppk_blockchain.blockchain.util;

import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;

public class CalculateTravelCosts {

    public static final int METERS_IN_KILOMETER = 1000;
    private final double HEFFING_SPITS = 1.5;
    private final double HEFFING_NIET_SPITS = 0.8;
    private final double PRICE_PER_KM = 0.06;
    private final double[] MULTIPLICATION_FACTOR_ROADCATEGORIES = {0.5, 0.75, 1, 1.25, 1.5};
    HashMap<String, Double> calcMap;

    public CalculateTravelCosts() {

        calcMap = new HashMap<>(); //This map contains all the different emissiontypes that the RDW has.
        calcMap.put("G", 1.2);
        calcMap.put("R", 0.6);
        calcMap.put("P", 0.8);
        calcMap.put("T", 1.4);
        calcMap.put("W", 2.0);
        calcMap.put("1", 1.6);
        calcMap.put("2", 1.4);
        calcMap.put("3", 1.3);
        calcMap.put("4", 1.2);
        calcMap.put("5", 1.1);
        calcMap.put("6", 1.0);
        calcMap.put("E", 0.8);
        calcMap.put("Z", 0.6);
    }

    /**
     *
     * @param roadCategory
     * @param spits
     * @param distanceTravelled
     * @return
     */
    public double calculateTravelCosts(Integer roadCategory, Integer spits, double distanceTravelled, String emissionType) {
        /*
        Divide to kilometers.
        Times road category
        Times value for emissie_code. Emissie=emission.
        Check if it is for spits or not.
         */
        double price = distanceTravelled / METERS_IN_KILOMETER * PRICE_PER_KM
                * MULTIPLICATION_FACTOR_ROADCATEGORIES[roadCategory - 1] * calcMap.get(emissionType);

        if(spits == 0) {
            price *= HEFFING_NIET_SPITS;
        } else {
            price *= HEFFING_SPITS;
        }
        return price;
    }

}
