package com.capgemini.ppk_blockchain.blockchain.clientinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import org.hyperledger.fabric.client.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public interface DriverAssetClientInterface extends GeneralAssetInterface {

    /**
     * @param driverId
     * @return
     */
    public boolean createDriverAsset(String driverId, String licensePlate, String emissionType, String brand);

    public boolean deleteDriverAsset(String driverId);

    boolean updateDriverAsset(DriverAsset driverAsset) throws GatewayException, CommitException;

    DriverAsset readDriverAsset(String driverAssetId) throws GatewayException;

    List<DriverAsset> retrieveAllAssets() throws GatewayException;

    List<DriverAsset> getHistoryForDriverAsset(String driverId) throws GatewayException;

}
