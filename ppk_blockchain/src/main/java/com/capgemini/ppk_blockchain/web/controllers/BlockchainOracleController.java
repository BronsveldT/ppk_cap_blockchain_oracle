package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.web.models.CarInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainOracleController {

    @GetMapping
    CarInfo retrieveCarInfo(@RequestBody CarInfo carInfo) {
        return carInfo;
    }

    @PostMapping("/car/processDrivingInformation")
    CarInfo postCarInfo(@RequestBody CarInfo carInfo) {

        return carInfo;
    }
}
