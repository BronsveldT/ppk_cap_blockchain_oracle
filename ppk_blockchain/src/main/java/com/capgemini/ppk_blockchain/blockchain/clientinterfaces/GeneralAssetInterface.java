package com.capgemini.ppk_blockchain.blockchain.clientinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface GeneralAssetInterface {

    boolean checkForAssetExistence(String assetId) throws GatewayException;

    List<Object> retrieveAllAssets() throws GatewayException;

}
