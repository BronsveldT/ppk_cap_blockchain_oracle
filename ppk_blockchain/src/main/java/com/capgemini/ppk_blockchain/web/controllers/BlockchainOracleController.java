package com.capgemini.ppk_blockchain.web.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.web.restmodels.CarInfo;
import com.capgemini.ppk_blockchain.web.services.DriverInfoProcessServiceImpl;
import com.capgemini.ppk_blockchain.web.services.DriverInfoRetrievalServiceImpl;
import com.capgemini.ppk_blockchain.web.services.RoadAssetRetrievalServiceImpl;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Driver;
import java.util.List;

@RestController
public class BlockchainOracleController {




    @GetMapping("/car/{id}/getHistory")
    CarInfo getHistoryForCar(@PathVariable String id) {
        return null;
    }
}
