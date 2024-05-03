package com.capgemini.ppk_blockchain.blockchain.model;

import java.util.Arrays;
import java.util.HashMap;

public class DriverAsset {

    private final int METERS_IN_KILOMETER = 1000;
    private final double HEFFING_SPITS = 1.5;
    private final double HEFFING_NIET_SPITS = 0.8;
    private final double PRICE_PER_KM = 0.06;
    private final double[] MULTIPLICATION_FACTOR_ROADCATEGORIES = {0.5, 0.75, 1, 1.25, 1.5};
    private String driverAssetId;
    private String licensePlate;
    private String brand;
    private String emissionType;
    private double[] drivenKilometersOnRoads = new double[5];

    private double rideCosts = 0.0;
    HashMap<String, Double> calcMap;

    public DriverAsset() {
    }

    public DriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {
        this.driverAssetId = driverAssetId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.emissionType = emissionType;

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

    public String getDriverAssetId() {
        return driverAssetId;
    }

    public void setDriverAssetId(String driverAssetId) {
        this.driverAssetId = driverAssetId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEmissionType() {
        return emissionType;
    }

    public void setEmissionType(String emissionType) {
        this.emissionType = emissionType;
    }

    /**
     *
     * @param roadCategory
     * @param streetName
     * @param distanceTravelled
     * @param spits
     */
    public void addTravelledInformation(Integer roadCategory,
                                        String streetName,
                                        double distanceTravelled,
                                        Integer spits){
        //1. Add the driven meters to the roadcategories array.
        drivenKilometersOnRoads[roadCategory - 1]  += distanceTravelled / METERS_IN_KILOMETER;
        //2. Add the costs of the meters to the price.
        double priceOfDrive = calculateTravelCosts(roadCategory, spits, distanceTravelled);
        this.rideCosts += priceOfDrive;
        System.out.println(this.rideCosts);
        System.out.println(Arrays.toString(this.drivenKilometersOnRoads));
    }

    /**
     *
     * @param roadCategory
     * @param spits
     * @param distanceTravelled
     * @return
     */
    private double calculateTravelCosts(Integer roadCategory, Integer spits, double distanceTravelled) {
        /*
        Divide to kilometers.
        Times road category
        Times value for emissie_code. Emissie=emission.
        Check it is for spits or not.
         */
        double price = distanceTravelled / METERS_IN_KILOMETER * PRICE_PER_KM
                * MULTIPLICATION_FACTOR_ROADCATEGORIES[roadCategory - 1] * calcMap.get(this.emissionType);

        if(spits == 0) {
            price *= HEFFING_NIET_SPITS;
        } else {
            price *= HEFFING_SPITS;
        }
        return price;
    }
}
