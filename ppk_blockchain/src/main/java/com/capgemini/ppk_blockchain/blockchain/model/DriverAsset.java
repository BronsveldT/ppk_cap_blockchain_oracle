package com.capgemini.ppk_blockchain.blockchain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
@ToString
public class DriverAsset {

    private String driverAssetId;
    private String licensePlate;
    private String brand;
    private String emissionType;
    private double[] drivenKilometersOnRoads = new double[5];

    private double rideCosts = 0.0;

    public DriverAsset() {
    }

    public DriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {
        this.driverAssetId = driverAssetId;
        this.licensePlate = licensePlate;
        this.brand = brand;
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
}
