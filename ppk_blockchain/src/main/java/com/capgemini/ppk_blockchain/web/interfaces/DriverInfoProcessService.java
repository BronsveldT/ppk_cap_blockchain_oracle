package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.models.CarInfo;

public interface DriverInfoProcessService {
    public abstract Boolean createCarAsset(String carId);
    public abstract Boolean processDriverInformation(CarInfo carInfo);
}
