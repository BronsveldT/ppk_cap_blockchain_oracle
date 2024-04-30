package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;

import java.util.List;

public interface DriverInfoRetrievalService {
    public abstract Boolean checkForDriverAssetExistence(String id);
    public abstract DriverAsset retrieveDriverAsset(String id);
    public abstract List<DriverAsset> retrieveAllDriverAssets();
}
