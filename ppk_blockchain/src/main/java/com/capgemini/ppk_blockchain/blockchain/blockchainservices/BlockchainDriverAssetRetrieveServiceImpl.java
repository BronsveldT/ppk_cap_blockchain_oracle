package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.DriverAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainDriverAssetRetrieveServiceInterface;
import com.capgemini.ppk_blockchain.blockchain.services.EncryptionService;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockchainDriverAssetRetrieveServiceImpl implements BlockchainDriverAssetRetrieveServiceInterface {

    private DriverAssetClient driverAssetClient;
    private final EncryptionService encryptionService;
    public BlockchainDriverAssetRetrieveServiceImpl(EncryptionService encryptionService) {

        try {
            driverAssetClient = new DriverAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.encryptionService = encryptionService;
    }

    @Override
    public DriverAsset retrieveDriverAsset(String driverAssetId) throws Exception {
        String encryptedKey = this.encryptionService.encrypt(driverAssetId);
        DriverAsset driverasset = driverAssetClient.readDriverAsset(encryptedKey);
        driverasset.setDriverAssetId(this.encryptionService.decrypt(driverasset.getDriverAssetId()));
        driverasset.setLicensePlate(this.encryptionService.decrypt(driverasset.getLicensePlate()));
        return driverasset;
    }

    @Override
    public List<DriverAsset> retrieveAllDriverAssets() throws GatewayException {
        return driverAssetClient.retrieveAllAssets();
    }

    @Override
    public boolean checkForDriverAssetExistence(String driverAssetId) throws Exception {
        String encryptedKey = this.encryptionService.encrypt(driverAssetId);
        return driverAssetClient.checkForAssetExistence(encryptedKey);
    }

    @Override
    public List<DriverAsset> getHistoryForDriverAsset(String driverAssetId) throws Exception {
        List<DriverAsset> driverAssetList = this.driverAssetClient.
                getHistoryForDriverAsset(this.encryptionService.encrypt(driverAssetId));

        driverAssetList = driverAssetList.stream().peek(obj -> {
            try {
                obj.setDriverAssetId(this.encryptionService.decrypt(obj.getDriverAssetId()));
                obj.setLicensePlate(this.encryptionService.decrypt(obj.getLicensePlate()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return driverAssetList;
    }
}
