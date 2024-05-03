package com.capgemini.ppk_blockchain.blockchain.interfaces;

import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;

public interface BlockchainService {
    public abstract void addCarInfoToDriverAsset(String driverAssetId, String licensePlate,
                                                 String brand, String emissionType);

    public abstract void addTravelInformationToDriverAsset(int roadCategory, String streetName,
                                                           RoadInformation roadInformation);

    public abstract void addRoadData(String roadAdminType, String streetName,
             Integer adminNumber,
             String adminName,
             String roadAdminName,
             double metersTravelled);

    public abstract void addRoadData(int roadCategory,
                                     String streetName,
                                     String municipality,
                                     String stateName,
                                     double distanceTravelledInMeters);
    public abstract void sendDataToBlockchain();
}
