package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainDriverAssetRetrieveServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoRetrievalService;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverInfoRetrievalServiceImpl implements DriverInfoRetrievalService {

    BlockchainDriverAssetRetrieveServiceImpl blockchainRetrieveService;
    @Override
    public Boolean checkForDriverAssetExistence(String id) throws Exception {
        return this.blockchainRetrieveService.checkForDriverAssetExistence(id);
    }

    @Override
    public DriverAsset retrieveDriverAsset(String id) throws Exception {
        return this.blockchainRetrieveService.retrieveDriverAsset(id);
    }

    @Override
    public List<Object> retrieveAllDriverAssets() throws GatewayException {
        return this.blockchainRetrieveService.retrieveAllDriverAssets();
    }
}
