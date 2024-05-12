package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface DriverInfoRetrievalService {
    public abstract Boolean checkForDriverAssetExistence(String id);
    public abstract DriverAsset retrieveDriverAsset(String id) throws GatewayException;
    public abstract List<DriverAsset> retrieveAllDriverAssets();
}
