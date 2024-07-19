package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.Road;

import java.util.List;

public interface RoadAssetRetrievalService {
    public abstract List<Road> retrieveRoads(String roadName);
    public abstract List<Road> retrieveRoadsByMunicipality(String municipalityName);
    public abstract List<Road> retrieveRoadsByAdminType(String adminType);
    public abstract List<Road> retrieveRoadsByState(String state);
}
