package com.capgemini.ppk_blockchain.blockchain.interfaces;

import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;

public interface BlockchainService {
    public abstract void addCarInfoToDriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType);
    public abstract void addTravelInformationToDriverAsset(int roadCategory, String streetName, int adminNumber , RoadInformation roadInformation);
    public abstract void sendDataToBlockchain();
}
