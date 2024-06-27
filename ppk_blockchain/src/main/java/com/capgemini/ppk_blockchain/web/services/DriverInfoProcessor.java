package com.capgemini.ppk_blockchain.web.services;


import com.capgemini.ppk_blockchain.web.repositories.ReverseRoadRepository;
import com.capgemini.ppk_blockchain.web.repositories.WegenRepository;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;

@Service
public class DriverInfoProcessor {

    private final WegenRepository wegenRepository;
    private final ReverseRoadRepository reverseRoadRepository;
    private final ReverseGeocodingService reverseGeocodingService;
    private final HashMap<String, Integer> roadsNotInDataset;

    public DriverInfoProcessor(WegenRepository wegenRepository, ReverseRoadRepository reverseRoadRepository,
                               ReverseGeocodingService reverseGeocodingService) {
        this.wegenRepository = wegenRepository;
        this.reverseRoadRepository = reverseRoadRepository;
        this.reverseGeocodingService = reverseGeocodingService;
        this.roadsNotInDataset = initializeRoadsNotInDataset();
    }

    private HashMap<String, Integer> initializeRoadsNotInDataset() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Doctor J.M. den Uylweg", 2);
        map.put("Thorbeckeweg", 2);
        map.put("Kolkweg", 2);
        map.put("Oostzanerdijk", 3);
        map.put("Coentunnelweg", 1);
        map.put("Ringweg-Noord", 1);
        map.put("Westrandweg", 1);
        map.put("Ringweg-West", 1);
        map.put("Rijksweg A5", 1);
        map.put("Rijksweg A4", 1);
        map.put("A5", 1);
        map.put("A4", 1);
        map.put("N468", 2);
        map.put("N223", 2);
        map.put("Klaas Engelbrechtsweg", 3);
        map.put("Burgemeester van der Goeslaan", 3);
        map.put("Woudseweg", 2);
        map.put("Oude Liermolenweg", 2);
        return map;
    }

    public double processDriverInformation(CarInfo carInfo) throws CommitException, GatewayException {
        blockchainService.addCarInfoToDriverAsset(carInfo.getKenteken(), carInfo.getKenteken(),
                carInfo.getMerk(), carInfo.getEmissieType());
        int skippedRoads = 0;
        for (RoadInformation roadinformation : carInfo.getRoadInformation()) {
            processRoadInformation(roadinformation);
        }
        try {
            blockchainService.sendDataToBlockchain();
        } catch (IOException | CertificateException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return blockchainService.retrieveRideCosts();
    }
}
