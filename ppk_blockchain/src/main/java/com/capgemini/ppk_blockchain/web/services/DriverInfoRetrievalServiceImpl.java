package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainDriverAssetRetrieveServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoRetrievalService;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverInfoRetrievalServiceImpl implements DriverInfoRetrievalService {

    final
    BlockchainDriverAssetRetrieveServiceImpl blockchainRetrieveService;

    public DriverInfoRetrievalServiceImpl(BlockchainDriverAssetRetrieveServiceImpl blockchainRetrieveService) {
        this.blockchainRetrieveService = blockchainRetrieveService;
    }

    @Override
    public Boolean checkForDriverAssetExistence(String id) throws Exception {
        return this.blockchainRetrieveService.checkForDriverAssetExistence(id);
    }

    @Override
    public DriverAsset retrieveDriverAsset(String id) throws Exception {
        return this.blockchainRetrieveService.retrieveDriverAsset(id);
    }

    @Override
    public List<DriverAsset> retrieveAllDriverAssets() throws GatewayException {
        return this.blockchainRetrieveService.retrieveAllDriverAssets();
    }

    @Override
    public List<DriverAsset> getHistoryForDriverAsset(String id) throws Exception {
        return this.blockchainRetrieveService.getHistoryForDriverAsset(id);
    }
}
