package com.capgemini.ppk_blockchain.web.services;


import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainDriverAssetCommitServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainRoadAssetCommitServiceImpl;
import com.capgemini.ppk_blockchain.web.db.models.ReverseRoads;
import com.capgemini.ppk_blockchain.web.db.models.Wegen;
import com.capgemini.ppk_blockchain.web.repositories.ReverseRoadRepository;
import com.capgemini.ppk_blockchain.web.repositories.WegenRepository;
import com.capgemini.ppk_blockchain.web.restmodels.Address;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.restmodels.OpenstreetMap;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DriverInfoProcessor {

    private final WegenRepository wegenRepository;
    private final ReverseRoadRepository reverseRoadRepository;
    private final ReverseGeocodingService reverseGeocodingService;
    private final BlockchainDriverAssetCommitServiceImpl blockchainDriverAssetCommitService;
    private final BlockchainRoadAssetCommitServiceImpl blockchainRoadAssetCommitService;
    private final HashMap<String, Integer> roadsNotInDataset;

    public DriverInfoProcessor(WegenRepository wegenRepository, ReverseRoadRepository reverseRoadRepository,
                               ReverseGeocodingService reverseGeocodingService, BlockchainDriverAssetCommitServiceImpl blockchainDriverAssetCommitService, BlockchainRoadAssetCommitServiceImpl blockchainRoadAssetCommitService) {
        this.wegenRepository = wegenRepository;
        this.reverseRoadRepository = reverseRoadRepository;
        this.reverseGeocodingService = reverseGeocodingService;
        this.blockchainDriverAssetCommitService = blockchainDriverAssetCommitService;
        this.blockchainRoadAssetCommitService = blockchainRoadAssetCommitService;
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

    /**
     *
     *
     * This method processes the ride that the driver has done and forwards it to the blockchain services.
     *
     *
     * @param carInfo
     * @return
     * @throws CommitException
     * @throws GatewayException
     *
     * @return The cost of the ride.
     */
    public double processDriverInformation(CarInfo carInfo) throws Exception {

        //As the car information is considered the driver information, we move the data to the DriverAsset DTO in the
        //blockchain package.

        blockchainDriverAssetCommitService.addCarInfoToDriverAsset(carInfo.getKenteken(), carInfo.getKenteken(),
                carInfo.getMerk(), carInfo.getEmissieType());
        int skippedRoads = 0;
        for (RoadInformation roadinformation : carInfo.getRoadInformation()) {
            processRoadInformation(roadinformation);
        }
        return 0.0;
//        try {
//            blockchainService.sendDataToBlockchain();
//        } catch (IOException | CertificateException | InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
//        return blockchainService.retrieveRideCosts();
    }

    private int determineRoadCategory(String roadName) {
        // Check if roadName matches the format Rijksweg A + number or A + number
        if (roadName.matches("(?i)Rijksweg A\\d+") || roadName.matches("(?i)A\\d+")) {
            return 1;
        }

        // Check if roadName matches the format N + numbers
        if (roadName.matches("(?i)N\\d+")) {
            return 2;
        }

        // Check if roadName contains certain keywords
        if (roadName.matches("(?i).*\\b(pad|dijk|steeg|water|polder)\\b.*")) {
            return 4;
        }

        // Default category if none of the above patterns match
        return 3;
    }

    private void processRoadInformation(RoadInformation roadinformation) {
        ReverseRoads reverseRoads = reverseRoadRepository.findByLatAndLon(roadinformation.getLatitude(), roadinformation.getLongitude());
        if (reverseRoads != null) {
            this.blockchainRoadAssetCommitService.addDataToRoad(
                    reverseRoads.getRoadAdminType(),
                    reverseRoads.getStreetName(),
                    reverseRoads.getAdminNumber(),
                    reverseRoads.getAdminName(),
                    reverseRoads.getRoadAdminName(),
                    roadinformation.getDistanceToPrev());
        } else {
            OpenstreetMap openstreetMap = reverseGeocodingService.reverseGeoCodingOpenStreetMap(roadinformation.getLatitude(), roadinformation.getLongitude());
            processOpenStreetMapData(openstreetMap, roadinformation);
        }
    }
//
    private void processOpenStreetMapData(OpenstreetMap openstreetMap, RoadInformation roadinformation) {
        if (openstreetMap != null) {
            Address address = openstreetMap.getAddress();
            if (roadsNotInDataset.containsKey(address.getRoad())) {
                addRoadData(address, roadinformation);
            } else {
                Wegen wegen = wegenRepository.findWegenByStreetName(address.getRoad(), address.getTown());
                if (wegen != null) {
                    addWegenData(wegen, roadinformation);
                }
            }
        }
    }
//
    private void addRoadData(Address address, RoadInformation roadinformation) {
        blockchainDriverAssetCommitService.addTravelInformationToDriverAsset(roadsNotInDataset.get(address.getRoad()), address.getRoad(), roadinformation);
        blockchainRoadAssetCommitService.addDataToRoad(
                roadsNotInDataset.get(address.getRoad()),
                address.getRoad(),
                address.getMunicipality(),
                address.getState(),
                roadinformation.getDistanceToPrev()
        );
    }
//
    private void addWegenData(Wegen wegen, RoadInformation roadinformation) {
        blockchainDriverAssetCommitService.addTravelInformationToDriverAsset(wegen.getRoadCategory(), wegen.getStreetName(), roadinformation);
        ReverseRoads reverseRoads = new ReverseRoads(
                roadinformation.getLatitude(),
                roadinformation.getLongitude(),
                wegen.getRoadCategory(),
                wegen.getStreetName(),
                wegen.getRoadAdminType(),
                wegen.getAdminNumber(),
                wegen.getAdminName(),
                wegen.getAdminName()
        );
        blockchainRoadAssetCommitService.addDataToRoad(
                reverseRoads.getRoadAdminType(),
                reverseRoads.getStreetName(),
                reverseRoads.getAdminNumber(),
                reverseRoads.getAdminName(),
                reverseRoads.getRoadAdminName(),
                roadinformation.getDistanceToPrev()
        );
        reverseRoadRepository.save(reverseRoads);
    }
}
