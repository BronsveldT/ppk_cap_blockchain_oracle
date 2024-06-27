package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoProcessService;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

@Service
public class DriverInfoProcessServiceImpl implements DriverInfoProcessService {

    private final DriverInfoProcessor driverInfoProcessor;

    private final WegenRepository wegenRepository;
    private final ReverseRoadRepository reverseRoadRepository;
    private final Block blockchainService;
    private final ReverseGeocodingService reverseGeocodingService;
//    private final EncryptionService encryptionService;

    public DriverInfoProcessServiceImpl(DriverInfoProcessor driverInfoProcessor, BlockchainServiceImpl blockchainService) {
        this.driverInfoProcessor = driverInfoProcessor;
        this.blockchainService = blockchainService;
    }

    @Override
    public double processDriverInformation(CarInfo carInfo) throws CommitException, GatewayException {
        return driverInfoProcessor.processDriverInformation(carInfo);
    }
}
