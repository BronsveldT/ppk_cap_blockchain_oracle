package com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public interface BlockchainRoadAssetRetrieveServiceInterface {

    public List<Road> readRoadAssetByName(String roadName);

    public List<Road> retrieveRoadsByMunicipality(String municipality);

    public List<Road> retrieveAllAssets() throws GatewayException;


}
