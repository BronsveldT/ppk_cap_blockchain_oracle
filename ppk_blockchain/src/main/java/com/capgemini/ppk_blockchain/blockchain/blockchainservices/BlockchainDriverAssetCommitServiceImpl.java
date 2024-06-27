package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.DriverAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainDriverAssetCommitServiceInterface;
import com.capgemini.ppk_blockchain.blockchain.util.CalculateTravelCosts;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.hyperledger.fabric.client.*;

public class BlockchainDriverAssetCommitServiceImpl implements BlockchainDriverAssetCommitServiceInterface {

    DriverAsset driverAsset;
    private DriverAssetClient driverAssetClient;
    private CalculateTravelCosts calculateTravelCosts;

    public BlockchainDriverAssetCommitServiceImpl() {
        try {
            driverAssetClient = new DriverAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        calculateTravelCosts = new CalculateTravelCosts();
    }

    @Override
    public boolean createAsset(String assetId) {
        return driverAssetClient.createDriverAsset(assetId);
    }

    @Override
    public boolean createAsset() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        return false;
    }

    @Override
    public boolean deleteAsset(String assetId) {
        return driverAssetClient.deleteDriverAsset(assetId);
    }

    @Override
    public boolean updateDriverAsset() throws CommitException, GatewayException {
        return driverAssetClient.updateDriverAsset(this.driverAsset);
    }

    /**
     *
     * @param driverAssetId
     * @param licensePlate
     * @param brand
     * @param emissionType
     */
    public void addCarInfoToDriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {

        this.driverAsset = new DriverAsset(driverAssetId, licensePlate, brand, emissionType);
    }

    public void addTravelInformationToDriverAsset(int roadCategory, String streetName, RoadInformation roadInformation) {
        this.driverAsset.getDrivenKilometersOnRoads()[roadCategory -1] += roadInformation.getDistanceToPrev() / CalculateTravelCosts.METERS_IN_KILOMETER;
        double rideCosts = this.driverAsset.getRideCosts();
        rideCosts += this.calculateTravelCosts.calculateTravelCosts(roadCategory, roadInformation.getSpits(),
                roadInformation.getDistanceToPrev(), this.driverAsset.getEmissionType());

        this.driverAsset.setRideCosts(rideCosts);
    }
}
