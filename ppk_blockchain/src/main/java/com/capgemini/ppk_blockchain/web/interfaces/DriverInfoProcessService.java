package com.capgemini.ppk_blockchain.web.interfaces;

import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;

public interface DriverInfoProcessService {
    public abstract Boolean createCarAsset(String carId);
    public abstract Boolean processDriverInformation(CarInfo carInfo);
}
