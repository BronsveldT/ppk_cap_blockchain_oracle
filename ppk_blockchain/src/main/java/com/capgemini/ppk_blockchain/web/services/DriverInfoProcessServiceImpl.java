package com.capgemini.ppk_blockchain.web.services;


import com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces.BlockchainDriverAssetCommitServiceInterface;
import com.capgemini.ppk_blockchain.blockchain.blockchainservices.BlockchainDriverAssetCommitServiceImpl;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoProcessService;
import com.capgemini.ppk_blockchain.web.repositories.ReverseRoadRepository;
import com.capgemini.ppk_blockchain.web.repositories.WegenRepository;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

@Service
public class DriverInfoProcessServiceImpl implements DriverInfoProcessService {

    private final DriverInfoProcessor driverInfoProcessor;

    private final WegenRepository wegenRepository;
    private final ReverseRoadRepository reverseRoadRepository;
    private final BlockchainDriverAssetCommitServiceInterface blockchainService;
    private final ReverseGeocodingService reverseGeocodingService;
//    private final EncryptionService encryptionService;

    public DriverInfoProcessServiceImpl(DriverInfoProcessor driverInfoProcessor, WegenRepository wegenRepository, ReverseRoadRepository reverseRoadRepository, ReverseGeocodingService reverseGeocodingService) {
        this.driverInfoProcessor = driverInfoProcessor;
        this.wegenRepository = wegenRepository;
        this.reverseRoadRepository = reverseRoadRepository;
        this.reverseGeocodingService = reverseGeocodingService;
        this.blockchainService = new BlockchainDriverAssetCommitServiceImpl();
    }

    @Override
    public double processDriverInformation(CarInfo carInfo) throws Exception {
        return driverInfoProcessor.processDriverInformation(carInfo);
    }
}
