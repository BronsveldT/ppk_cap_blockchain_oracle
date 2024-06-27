package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface DriverInfoRetrievalService {
    public abstract Boolean checkForDriverAssetExistence(String id) throws Exception;
    public abstract DriverAsset retrieveDriverAsset(String id) throws Exception;
    public abstract List<Object> retrieveAllDriverAssets() throws GatewayException;
}
