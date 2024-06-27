package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.services.DriverInfoProcessServiceImpl;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driverassets")
public class DriverAssetController {

    private final DriverInfoProcessServiceImpl driverInfoProcessService;

    @Autowired
    public DriverAssetController(DriverInfoProcessServiceImpl driverInfoProcessService) {
        this.driverInfoProcessService = driverInfoProcessService;
    }


    /**
     * This function on the mobile application side gets called when the user finishes driving.
     * This function only processes the information of a ride from the user. To retrieve general car information such as
     * distance driven on specific road categories see {@link #retrieveCarInfo(String)}
     *
     * @param carInfo
     * @return
     */
    @PostMapping("/process")
    double processRideOfCar(@RequestBody CarInfo carInfo) throws Exception {
        this.driverInfoProcessService.processDriverInformation(carInfo);
        return 0.0;
        //        return driverInfoProcessService.processDriverInformation(carInfo);
    }

    /**
     * We check for the existence of the car. If the car already exists in the ledger, we just return a true.
     * If the car doesn't exist in the ledger, we will create a new asset in the Ledger and return true
     * If somehow an error occurs, we return false.
     * <p>
     * On the mobile application side, this function gets called when the user starts driving.
     * <p>
     * In a real project this can cause issues due to the following: There needs to be some form of authentication of
     * the owner of the mobile that makes requests to the blockchain oracle and the car itself.
     * For example if A owns Car 1 and owns Mobile 1. For Car 1, requests to the oracle to make changes for Car 1 should
     * only come from mobile 1. If Car 1 is a family car and is used by multiple people, there should be a way in the
     * mobile application to add Mobile 2 to the list of approved mobiles that can make requests to the oracle for Car 1.
     * This is to ensure another user cannot drive distances on their own car while adding distances to Car 1.
     * <p>
     * The above information is irrelevant if the project eventually moves on to adding closed off boxes in cars and
     * the boxes get used to keep track of how much distance a car has driven.
     *
     * @param carId: The carId is used to create a unique Asset in the ledger.
     * @return false/true depending on the existence of the Asset.
     */
    @GetMapping("/checkForExistence/{driverAssetId}")
    boolean checkForExistenceCar(@PathVariable String carId) {
        System.out.println(carId);
        boolean existenceCar = false;
        //        if (!driverInfoRetrievalService.checkForDriverAssetExistence(carId)) {
        //            driverInfoProcessService.createCarAsset(carId);
        //            existenceCar = true;
        //        }
        return existenceCar;
    }

    /**
     * The public available request point to retrieve information about a specific car from the ledger.
     * To process information of a single ride by the user see {@link #processRideOfCar(CarInfo)}
     *
     * @param id
     * @return
     */
    @GetMapping("/retrieve/{id}")
    DriverAsset retrieveCarInfo(@PathVariable String id) throws GatewayException {
//        if(driverInfoRetrievalService.checkForDriverAssetExistence(id)) {
//            return driverInfoRetrievalService.retrieveDriverAsset(id);
//        }
        return null;
    }

    @GetMapping("/{id}/getHistory")
    CarInfo getHistoryForCar(@PathVariable String id) {
        return null;
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<DriverAsset>> getAllDriverAssets() {
        return null;
    }
}
