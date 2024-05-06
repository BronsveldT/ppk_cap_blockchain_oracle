package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoRetrievalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverInfoRetrievalServiceImpl implements DriverInfoRetrievalService {
    @Override
    public Boolean checkForDriverAssetExistence(String id) {
        return null;
    }

    @Override
    public DriverAsset retrieveDriverAsset(String id) {
        return null;
    }

    @Override
    public List<DriverAsset> retrieveAllDriverAssets() {
        return null;
    }
}
