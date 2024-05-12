package com.capgemini.ppk_blockchain.blockchain.interfaceimpl;

import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainDriverAssetController;
import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainRoadAssetController;
import com.capgemini.ppk_blockchain.blockchain.interfaces.BlockchainRetrievalService;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockchainRetrieveServiceImpl implements BlockchainRetrievalService {
    BlockchainRoadAssetController blockchainRoadAssetController;
    BlockchainDriverAssetController blockchainDriverAssetController;

    @Override
    public DriverAsset retrieveDriverAsset(String driverId) throws GatewayException {
        return this.blockchainDriverAssetController.readDriverAsset(driverId);
    }

    @Override
    public List<DriverAsset> retrieveDriverAssets() {
        return List.of();
    }

    @Override
    public List<Road> retrieveRoads(String roadName) {
        return this.blockchainRoadAssetController.readRoadAsset(roadName);
    }

    @Override
    public List<Road> retrieveRoadsByMunicipality(String municipality) {
        return this.blockchainRoadAssetController.retrieveRoadsByMunicipality(municipality);
    }

    @Override
    public boolean checkForDriverAssetExists(String driverId) {
        return this.blockchainDriverAssetController.checkForDriverAssetExistence(driverId);
    }


}
