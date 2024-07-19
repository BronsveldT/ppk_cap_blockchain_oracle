package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.RoadAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainRoadAssetCommitServiceInterface;
import com.capgemini.ppk_blockchain.blockchain.util.CalculateTravelCosts;
import org.hyperledger.fabric.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BlockchainRoadAssetCommitServiceImpl implements BlockchainRoadAssetCommitServiceInterface {
    private Road road;
    private RoadAssetClient roadAssetClient;

    public BlockchainRoadAssetCommitServiceImpl() {

        try {
            this.roadAssetClient = new RoadAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean createAsset(String assetId) {
        return false;
    }

    @Override
    public boolean createAsset() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        return this.roadAssetClient.createRoadAsset(this.road);
    }

    @Override
    public boolean deleteAsset(String assetId) {
        return false;
    }

    @Override
    public void addDataToRoad(String roadAdminType, String streetName, Integer adminNumber, String adminName, String roadAdminName, double metersTravelled) {
        if(this.road != null) {
            Road roadCompare = new Road(roadAdminType,
                    streetName,
                    adminNumber,
                    adminName,
                    roadAdminName);
            if(!this.road.equals(roadCompare)) {
                System.out.println("Sturen data door naar blockchain voor de weg.");
                try {
                    this.sendRoadDataToBlockchain();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CommitException e) {
                    e.printStackTrace();
                } catch (GatewayException e) {
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
        this.road.addDistanceTravelled(metersTravelled / CalculateTravelCosts.METERS_IN_KILOMETER);
    }

    @Override
    public void addDataToRoad(int roadCategory, String streetName, String municipality, String stateName, double distanceTravelledInMeters) {
        if(this.road != null) {
            Road roadCompare = new Road(roadCategory,
                    streetName,
                    municipality,
                    stateName);
            if(!this.road.equalsSkippedRoad(roadCompare)) {
                System.out.println("Sturen data door naar blockchain voor de weg.");
                try {
                    this.sendRoadDataToBlockchain();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CommitException e) {
                    e.printStackTrace();
                } catch (GatewayException e) {
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
        this.road.addDistanceTravelled(distanceTravelledInMeters / CalculateTravelCosts.METERS_IN_KILOMETER);
    }

    @Override
    public boolean sendRoadDataToBlockchain() throws IOException, GatewayException, CommitException {
        System.out.println("Sturen data van de weg naar de Blockchain");
        this.road.setRoadId(String.valueOf(road.hashCode()));
        return true;
//        if(!this.roadAssetClient.checkForAssetExistence(this.road.getRoadId())) {
//            return this.roadAssetClient.createRoadAsset(this.road);
//        } else {
//            return this.roadAssetClient.updateRoadAsset(this.road);
//        }
    }
}
