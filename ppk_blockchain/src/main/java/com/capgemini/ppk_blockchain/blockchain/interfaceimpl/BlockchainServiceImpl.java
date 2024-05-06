package com.capgemini.ppk_blockchain.blockchain.interfaceimpl;

import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainDriverAssetController;
import com.capgemini.ppk_blockchain.blockchain.controllers.BlockchainRoadAssetController;
import com.capgemini.ppk_blockchain.blockchain.interfaces.BlockchainService;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BlockchainServiceImpl implements BlockchainService {
    private static final int KILOMETERS_IN_METER = 1000;
    DriverAsset driverAsset;
    Road road = null;

    BlockchainDriverAssetController blockchainController;
    BlockchainRoadAssetController blockchainRoadAssetController;

    @Override
    public void addCarInfoToDriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {
        this.driverAsset = new DriverAsset(driverAssetId, licensePlate, brand, emissionType);

    }

    @Override
    public void addTravelInformationToDriverAsset(int roadCategory, String streetName, RoadInformation roadInformation) {
        this.driverAsset.addTravelledInformation(roadCategory,
                streetName,
                roadInformation.getDistanceToPrev(),
                roadInformation.getSpits());
    }

    @Override
    public void addRoadData(String roadAdminType, String streetName,
                            Integer adminNumber,
                            String adminName, String roadAdminName,
                            double metersTravelled) {
        if(this.road != null) {
            Road roadCompare = new Road(roadAdminType,
                    streetName,
                    adminNumber,
                    adminName,
                    roadAdminName);
            if(!this.road.equals(roadCompare)) {
                System.out.println("Sturen data door naar blockchain voor de weg.");
                try {
                    this.sendRoadDataToBlockchain(road);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                road = roadCompare;
                return;
            }

        } else {
            System.out.println("Nieuwe weg");
            this.road = new Road(roadAdminType,
                    streetName,
                    adminNumber,
                    adminName,
                    roadAdminName);
        }
        this.road.addDistanceTravelled(metersTravelled / KILOMETERS_IN_METER);
    }

    @Override
    public void addRoadData(int roadCategory, String streetName, String municipality, String stateName, double distanceTravelledInMeters) {
        if(this.road != null) {
            Road roadCompare = new Road(roadCategory,
                    streetName,
                    municipality,
                    stateName);
            if(!this.road.equalsSkippedRoad(roadCompare)) {
                System.out.println("Sturen data door naar blockchain voor de weg.");
                try {
                    this.sendRoadDataToBlockchain(road);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                road = roadCompare;
                return;
            }

        } else {
            System.out.println("Nieuwe weg");
            this.road = new Road(roadCategory,
                    streetName,
                    municipality,
                    stateName);
        }
        this.road.addDistanceTravelled(distanceTravelledInMeters / KILOMETERS_IN_METER);
    }

    @Override
    public void sendDataToBlockchain() throws IOException {
//        this.blockchainController = new BlockchainDriverAssetController();
//        this.blockchainController.updateDriverAsset(this.driverAsset);
        System.out.println(driverAsset.toString());
    }

    /**
     *
     * @param road
     * @throws IOException
     */
    private void sendRoadDataToBlockchain(Road road) throws IOException {
//        streetName + adminNumber + roadAdminType
        road.setRoadId(String.valueOf(road.hashCode()));

        this.blockchainRoadAssetController = new BlockchainRoadAssetController();
        if(!this.blockchainRoadAssetController.checkForRoadAssetExistence(road.getRoadId())) {
            this.blockchainRoadAssetController.createDriverAsset(road);
        } else {
            this.blockchainRoadAssetController.updateRoadAsset(road);
        }
    }
}
