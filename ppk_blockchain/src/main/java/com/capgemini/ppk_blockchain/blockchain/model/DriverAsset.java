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
    private double[] drivenKilometersOnRoad = new double[5];

    private double rideCosts = 0.0;
    private Long timestampDriverAsset;
    public DriverAsset() {
    }

    public DriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {
        this.driverAssetId = driverAssetId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.emissionType = emissionType;


    }

    public DriverAsset(String driverAssetId, double rideCosts, double[] drivenKilometersOnRoad) {
        this.driverAssetId = driverAssetId;
        this.rideCosts = rideCosts;
        this.drivenKilometersOnRoad = drivenKilometersOnRoad;
    }

    public DriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType, double[] drivenKilometersOnRoad, double rideCosts,
                       long timestampDriverAsset) {
        this.driverAssetId = driverAssetId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.emissionType = emissionType;
        this.drivenKilometersOnRoad = drivenKilometersOnRoad;
        this.rideCosts = rideCosts;
        this.timestampDriverAsset = timestampDriverAsset;
    }
}
