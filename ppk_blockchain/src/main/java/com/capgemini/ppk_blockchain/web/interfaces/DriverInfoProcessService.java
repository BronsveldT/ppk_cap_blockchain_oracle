package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.restmodels.RoadTripInformation;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;

public interface DriverInfoProcessService {
    public abstract RoadTripInformation processDriverInformation(CarInfo carInfo) throws Exception;
}
