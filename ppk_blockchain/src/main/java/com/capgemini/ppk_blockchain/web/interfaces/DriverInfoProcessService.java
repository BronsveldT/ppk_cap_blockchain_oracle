package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;

public interface DriverInfoProcessService {
    public abstract double processDriverInformation(CarInfo carInfo) throws CommitException, GatewayException;
}
