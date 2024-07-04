package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.DriverAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainDriverAssetCommitServiceInterface;
import com.capgemini.ppk_blockchain.blockchain.services.EncryptionService;
import com.capgemini.ppk_blockchain.blockchain.util.CalculateTravelCosts;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainDriverAssetCommitServiceImpl implements BlockchainDriverAssetCommitServiceInterface {

    DriverAsset driverAsset;
    private DriverAssetClient driverAssetClient;
    private final CalculateTravelCosts calculateTravelCosts;
    @Autowired
    EncryptionService encryptionService;

    public BlockchainDriverAssetCommitServiceImpl() {
        try {
            driverAssetClient = new DriverAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        calculateTravelCosts = new CalculateTravelCosts();
    }

    @Override
    public boolean createAsset(String assetId) throws Exception {
        String encryptedKey = this.encryptionService.encrypt(assetId);
        this.driverAsset.setDriverAssetId(encryptedKey);
        this.driverAsset.setLicensePlate(encryptedKey);
        return driverAssetClient.createDriverAsset(this.driverAsset.getDriverAssetId(), this.driverAsset.getLicensePlate(), this.driverAsset.getEmissionType(), this.driverAsset.getBrand());
    }

    @Override
    public boolean createAsset() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        return false;
    }

    @Override
    public boolean deleteAsset(String assetId) throws Exception {
        String encryptedKey = this.encryptionService.encrypt(assetId);
        return driverAssetClient.deleteDriverAsset(encryptedKey);
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
    public void addCarInfoToDriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) throws Exception {
        String encryptedKey = this.encryptionService.encrypt(driverAssetId);
        this.driverAsset = new DriverAsset(encryptedKey, encryptedKey, brand, emissionType);
        System.out.println("Encrypted key: " + this.driverAsset.getDriverAssetId());
        System.out.println(this.driverAsset);
    }

    public void addTravelInformationToDriverAsset(int roadCategory, String streetName, RoadInformation roadInformation) {
        this.driverAsset.getDrivenKilometersOnRoad()[roadCategory -1] += roadInformation.getDistanceToPrev() / CalculateTravelCosts.METERS_IN_KILOMETER;
        double rideCosts = this.driverAsset.getRideCosts();
        rideCosts += this.calculateTravelCosts.calculateTravelCosts(roadCategory, roadInformation.getSpits(),
                roadInformation.getDistanceToPrev(), this.driverAsset.getEmissionType());

        this.driverAsset.setRideCosts(rideCosts);
    }

    public double retrieveRideCosts() {
        return this.driverAsset.getRideCosts();
    }
}
