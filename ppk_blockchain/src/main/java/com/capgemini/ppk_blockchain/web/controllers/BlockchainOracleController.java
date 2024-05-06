package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.services.DriverInfoProcessServiceImpl;
import com.capgemini.ppk_blockchain.web.services.DriverInfoRetrievalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlockchainOracleController {

    @Autowired
    DriverInfoProcessServiceImpl driverInfoProcessService;
    @Autowired
    DriverInfoRetrievalServiceImpl driverInfoRetrievalService;
    /**
     * The public available request point to retrieve information about a specific car from the ledger.
     * To process information of a single ride by the user see {@link #processRideOfCar(CarInfo)}
     * @param String id
     * @return
     */
    @GetMapping("/car/retrieve/{id}")
    DriverAsset retrieveCarInfo(@PathVariable String id) {
        if(driverInfoRetrievalService.checkForDriverAssetExistence(id)) {
            return driverInfoRetrievalService.retrieveDriverAsset(id);
        }
        return null;
    }

    @GetMapping("/car/{id}/getHistory")
    CarInfo getHistoryForCar(@PathVariable String id) {
        return null;
    }

    /**
     * We check for the existence of the car. If the car already exists in the ledger, we just return a true.
     * If the car doesn't exist in the ledger, we will create a new asset in the Ledger and return true
     * If somehow an error occurs, we return false.
     *
     * On the mobile application side, this function gets called when the user starts driving.
     *
     * In a real project this can cause issues due to the following: There needs to be some form of authentication of
     * the owner of the mobile that makes requests to the blockchain oracle and the car itself.
     * For example if A owns Car 1 and owns Mobile 1. For Car 1, requests to the oracle to make changes for Car 1 should
     * only come from mobile 1. If Car 1 is a family car and is used by multiple people, there should be a way in the
     * mobile application to add Mobile 2 to the list of approved mobiles that can make requests to the oracle for Car 1.
     * This is to ensure another user cannot drive distances on their own car while adding distances to Car 1.
     *
     * The above information is irrelevant if the project eventually moves on to adding closed off boxes in cars and
     * the boxes get used to keep track of how much distance a car has driven.
     * @param carId: The carId is used to create a unique Asset in the ledger.
     * @return false/true depending on the existence of the Asset.
     *
     */
    @GetMapping("/car/checkForExistence/{carId}")
    boolean checkForExistenceCar(@PathVariable String carId) {
        boolean existenceCar = false;
        if (!driverInfoRetrievalService.checkForDriverAssetExistence(carId)) {
            driverInfoProcessService.createCarAsset(carId);
            existenceCar = true;
        }
        return existenceCar;
    }

    /**
     *
     * @return
     */
    @GetMapping("api/v1/protected/all")
    public ResponseEntity<List<DriverAsset>> getAllDriverAssets() {
        return null;
    }
    /**
     * This function on the mobile application side gets called when the user finishes driving.
     * This function only processes the information of a ride from the user. To retrieve general car information such as
     * distance driven on specific road categories see {@link #retrieveCarInfo(CarInfo)}
     * @param carInfo
     * @return
     */
    @PostMapping("/car/processDrivingInformation")
    CarInfo processRideOfCar(@RequestBody CarInfo carInfo) {
        driverInfoProcessService.processDriverInformation(carInfo);
        return carInfo;
    }
    
    @GetMapping
    List<Road> retrieveRoadAsset(@PathVariable String roadName) {

    }
}
