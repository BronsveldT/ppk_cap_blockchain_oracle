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
    public DriverAsset createDriverAsset(String driverId);

    DriverAsset updateDriverAsset(DriverAsset driverAsset) throws GatewayException, CommitException;

    DriverAsset readDriverAsset(String driverAssetId) throws GatewayException;

}
