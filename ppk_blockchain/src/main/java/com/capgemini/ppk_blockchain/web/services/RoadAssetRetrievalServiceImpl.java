package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainRoadAssetRetrieveServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.interfaces.RoadAssetRetrievalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadAssetRetrievalServiceImpl implements RoadAssetRetrievalService {

    BlockchainRoadAssetRetrieveServiceImpl blockchainRoadAssetRetrieveService;

    public RoadAssetRetrievalServiceImpl(BlockchainRoadAssetRetrieveServiceImpl blockchainRoadAssetRetrieveService) {
        this.blockchainRoadAssetRetrieveService = blockchainRoadAssetRetrieveService;
    }

    @Override
    public List<Road> retrieveRoads(String roadName) {
        return this.blockchainRoadAssetRetrieveService.readRoadAssetByName(roadName);
    }

    @Override
    public List<Road> retrieveRoadsByMunicipality(String municipalityName) {
        return this.blockchainRoadAssetRetrieveService.retrieveRoadsByMunicipality(municipalityName);
    }

    @Override
    public List<Road> retrieveRoadsByAdminType(String adminType) {
        return this.blockchainRoadAssetRetrieveService.retrieveRoadsByAdminType(adminType);
    }

    @Override
    public List<Road> retrieveRoadsByState(String state) {
        return this.blockchainRoadAssetRetrieveService.retrieveRoadsByState(state);
    }
}
