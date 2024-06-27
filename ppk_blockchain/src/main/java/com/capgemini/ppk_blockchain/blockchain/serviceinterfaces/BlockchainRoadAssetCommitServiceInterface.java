package com.capgemini.ppk_blockchain.blockchain.serviceinterfaces;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;

import java.io.IOException;

public interface BlockchainRoadAssetCommitServiceInterface extends BlockchainCommitServiceInterface {

    public void addDataToRoad(String roadAdminType, String streetName,
                              Integer adminNumber,
                              String adminName, String roadAdminName,
                              double metersTravelled);

    public void addDataToRoad(int roadCategory, String streetName, String municipality, String stateName, double distanceTravelledInMeters);

    public boolean sendRoadDataToBlockchain() throws IOException, GatewayException, CommitException;
}
