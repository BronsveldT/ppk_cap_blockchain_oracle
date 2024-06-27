package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.interfaceimpl.BlockchainRetrieveServiceImpl;

import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainRoadAssetController;
import com.capgemini.ppk_blockchain.blockchain.interfaces.BlockchainRetrievalService;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.interfaces.RoadAssetRetrievalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadAssetRetrievalServiceImpl implements RoadAssetRetrievalService {

    BlockchainRetrieveServiceImpl blockchainRetrieveService;

    @Override
    public List<Road> retrieveRoads(String roadName) {
        return this.blockchainRetrieveService.retrieveRoads(roadName);
    }

    @Override
    public List<Road> retrieveRoadsByMunicipality(String municipalityName) {
        return this.blockchainRetrieveService.retrieveRoadsByMunicipality(municipalityName);
    }
}
