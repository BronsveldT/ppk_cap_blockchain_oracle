package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.RoadAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainRoadAssetRetrieveServiceInterface;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockchainRoadAssetRetrieveServiceImpl implements BlockchainRoadAssetRetrieveServiceInterface {

    private RoadAssetClient roadAssetClient;

    public BlockchainRoadAssetRetrieveServiceImpl() {

        try {
            this.roadAssetClient = new RoadAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Road> readRoadAssetByName(String roadName) {
        return this.roadAssetClient.readRoadAssetByName(roadName);
    }

    @Override
    public List<Road> retrieveRoadsByMunicipality(String municipality) {
        return this.roadAssetClient.retrieveRoadsByMunicipality(municipality);
    }

    @Override
    public List<Object> retrieveAllAssets() throws GatewayException {
        return this.roadAssetClient.retrieveAllAssets();
    }
}
