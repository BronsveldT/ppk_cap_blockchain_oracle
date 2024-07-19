package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.restmodels.RoadTripInformation;
import com.capgemini.ppk_blockchain.web.services.DriverInfoProcessServiceImpl;
import com.capgemini.ppk_blockchain.web.services.DriverInfoRetrievalServiceImpl;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Driver;
import java.util.List;

@RestController
@RequestMapping("/driverassets")
public class DriverAssetController {

    private final DriverInfoProcessServiceImpl driverInfoProcessService;
    private final DriverInfoRetrievalServiceImpl driverInfoRetrievalService;

    @Autowired
    public DriverAssetController(DriverInfoProcessServiceImpl driverInfoProcessService, DriverInfoRetrievalServiceImpl driverInfoRetrievalService) {
        this.driverInfoProcessService = driverInfoProcessService;

        this.driverInfoRetrievalService = driverInfoRetrievalService;
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
    RoadTripInformation processRideOfCar(@RequestBody CarInfo carInfo) throws Exception {
        Long startTime = System.currentTimeMillis();
        RoadTripInformation roadTripInformation = this.driverInfoProcessService.processDriverInformation(carInfo);
        Long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return roadTripInformation;
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
    boolean checkForExistenceCar(@PathVariable String driverAssetId) throws Exception {
        System.out.println(driverAssetId);
        return driverInfoRetrievalService.checkForDriverAssetExistence(driverAssetId);
    }

    /**
     * The public available request point to retrieve information about a specific car from the ledger.
     * To process information of a single ride by the user see {@link #processRideOfCar(CarInfo)}
     *
     * @param id
     * @return
     */
    @GetMapping("/retrieve/{id}")
    DriverAsset retrieveCarInfo(@PathVariable String id) throws Exception {
        if(driverInfoRetrievalService.checkForDriverAssetExistence(id)) {
            return driverInfoRetrievalService.retrieveDriverAsset(id);
        }
        return null;
    }

    @GetMapping("retrieve/{id}/getHistory")
    List<DriverAsset> getHistoryForCar(@PathVariable String id) {

        try {
            if(driverInfoRetrievalService.checkForDriverAssetExistence(id)) {
                return driverInfoRetrievalService.getHistoryForDriverAsset(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public List<DriverAsset> getAllDriverAssets() throws GatewayException {

        return driverInfoRetrievalService.retrieveAllDriverAssets();
    }
}
