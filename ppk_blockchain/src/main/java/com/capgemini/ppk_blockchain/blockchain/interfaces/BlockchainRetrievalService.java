package com.capgemini.ppk_blockchain.blockchain.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;

import java.util.List;

public interface BlockchainRetrievalService {

    public abstract DriverAsset retrieveDriverAsset(String driverId);
    public abstract List<DriverAsset> retrieveDriverAssets();
    public abstract List<Road> retrieveRoads(String roadName);
    public abstract List<Road> retrieveRoadsByMunicipality(String municipality);
    public abstract boolean checkForDriverAssetExists(String driverId);
}
