package com.capgemini.ppk_blockchain.blockchain.interfaceimpl;

import com.capgemini.ppk_blockchain.blockchain.interfaces.BlockchainService;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.restmodels.RoadInformation;
import org.springframework.stereotype.Service;

@Service
public class BlockchainServiceImpl implements BlockchainService {
    DriverAsset driverAsset;
    @Override
    public void addCarInfoToDriverAsset(String driverAssetId, String licensePlate, String brand, String emissionType) {
        this.driverAsset = new DriverAsset(driverAssetId, licensePlate, brand, emissionType);

    }

    @Override
    public void addTravelInformationToDriverAsset(int roadCategory, String streetName, int adminNumber, RoadInformation roadInformation) {
        this.driverAsset.addTravelledInformation(roadCategory,
                streetName,
                adminNumber,
                roadInformation.getDistanceToPrev(),
                roadInformation.getSpits());
    }

    @Override
    public void sendDataToBlockchain() {

    }
}
