package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.services.RoadAssetRetrievalServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roads")
public class RoadController {

    private final RoadAssetRetrievalServiceImpl roadAssetRetrievalService;

    public RoadController(RoadAssetRetrievalServiceImpl roadAssetRetrievalService) {
        this.roadAssetRetrievalService = roadAssetRetrievalService;
    }

    @GetMapping("/retrieve/road/{roadName}")
    List<Road> retrieveRoadAsset(@PathVariable String roadName) {
        return this.roadAssetRetrievalService.retrieveRoads(roadName);
    }

    @GetMapping("/retrieve/municipality/{municipality}")
    List<Road> retrieveRoadsByMunicipality(@PathVariable String municipality) {
        return this.roadAssetRetrievalService.retrieveRoadsByMunicipality(municipality);
    }

    @GetMapping("/retrieve/adminType/{adminType}")
    List<Road> retrieveRoadsByAdminType(@PathVariable String adminType) {
        return this.roadAssetRetrievalService.retrieveRoadsByAdminType(adminType);
    }

    @GetMapping("/retrieve/state/{state}")
    List<Road> retrieveRoadsByState(@PathVariable String state) {
        return this.roadAssetRetrievalService.retrieveRoadsByState(state);
    }
}
