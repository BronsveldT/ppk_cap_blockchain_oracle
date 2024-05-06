package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.Road;

import java.util.List;

public interface RoadAssetRetrievalService {
    public abstract List<Road> retrieveRoads(String roadName);
}
