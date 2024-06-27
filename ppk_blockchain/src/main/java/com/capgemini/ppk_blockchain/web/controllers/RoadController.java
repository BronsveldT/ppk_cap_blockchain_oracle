package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roads")
public class RoadController {

    @GetMapping("/road/retrieve/{roadName}")
    List<Road> retrieveRoadAsset(@PathVariable String roadName) {
        return null;
//        return this.roadAssetRetrievalService.retrieveRoads(roadName);
    }

    @GetMapping("/road/retrieve/{municipality}")
    List<Road> retrieveRoadsByMunicipality(@PathVariable String municipality) {
        return null;
//        return this.roadAssetRetrievalService.retrieveRoadsByMunicipality(municipality);
    }
}
