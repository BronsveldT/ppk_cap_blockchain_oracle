package com.capgemini.ppk_blockchain.blockchain.interfaceimpl;

import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainRoadAssetController;
import com.capgemini.ppk_blockchain.blockchain.interfaces.BlockchainRetrievalService;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockchainRetrieveServiceImpl implements BlockchainRetrievalService {
    BlockchainRoadAssetController blockchainRoadAssetController;


    @Override
    public DriverAsset retrieveDriverAsset(String driverId) {
        return null;
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


}
