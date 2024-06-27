package com.capgemini.ppk_blockchain.blockchain.serviceinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface BlockchainDriverAssetRetrieveServiceInterface {

    public DriverAsset retrieveDriverAsset(String driverAssetId) throws GatewayException;

    public List<Object> retrieveAllDriverAssets() throws GatewayException;
}
