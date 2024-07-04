package com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface BlockchainDriverAssetRetrieveServiceInterface {

    public DriverAsset retrieveDriverAsset(String driverAssetId) throws Exception;

    public List<DriverAsset> retrieveAllDriverAssets() throws GatewayException;

    public boolean checkForDriverAssetExistence(String driverAssetId) throws Exception;

    public List<DriverAsset> getHistoryForDriverAsset(String driverAssetId) throws Exception;
}
