package com.capgemini.ppk_blockchain.blockchain.blockchainservices;

import com.capgemini.ppk_blockchain.blockchain.clients.DriverAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainDriverAssetRetrieveServiceInterface;
import org.hyperledger.fabric.client.GatewayException;

import java.util.List;

public class BlockchainDriverAssetRetrieveServiceImpl implements BlockchainDriverAssetRetrieveServiceInterface {

    private DriverAssetClient driverAssetClient;

    public BlockchainDriverAssetRetrieveServiceImpl() {

        try {
            driverAssetClient = new DriverAssetClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public DriverAsset retrieveDriverAsset(String driverAssetId) throws GatewayException {
        return driverAssetClient.readDriverAsset(driverAssetId);
    }

    @Override
    public List<Object> retrieveAllDriverAssets() throws GatewayException {
        return driverAssetClient.retrieveAllAssets();
    }
}
