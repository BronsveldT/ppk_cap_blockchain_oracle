package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.interfaceimpl.BlockchainProcessServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.interfaceimpl.BlockchainRetrieveServiceImpl;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoRetrievalService;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverInfoRetrievalServiceImpl implements DriverInfoRetrievalService {

    BlockchainRetrieveServiceImpl blockchainRetrieveService;
    @Override
    public Boolean checkForDriverAssetExistence(String id) {
        return this.blockchainRetrieveService.checkForDriverAssetExists(id);
    }

    @Override
    public DriverAsset retrieveDriverAsset(String id) throws GatewayException {
        return this.blockchainRetrieveService.retrieveDriverAsset(id);
    }

    @Override
    public List<DriverAsset> retrieveAllDriverAssets() {
        return null;
    }
}
