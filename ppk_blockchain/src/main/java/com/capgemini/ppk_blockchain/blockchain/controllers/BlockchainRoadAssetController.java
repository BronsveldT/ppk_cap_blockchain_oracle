package com.capgemini.ppk_blockchain.blockchain.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class BlockchainRoadAssetController {

    Network network;
    Gateway.Builder builder;
    Contract contract;
    Gson gson;
    long seconds = 20;

    public BlockchainRoadAssetController() throws IOException {
        Path walletDirectory = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletDirectory);

        Path networkConfigPath = Paths.get("../","network", "network-specifics",
                "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        builder = Gateway.createBuilder().identity(wallet, "appUser")
                .commitTimeout(seconds, TimeUnit.SECONDS)
                .networkConfig(networkConfigPath).discovery(true);

        gson = new Gson().newBuilder().create();
    }

    /**
     *
     * @param roadAssetId
     * @return
     */
    public boolean checkForRoadAssetExistence(String roadAssetId) {
        String resp = null;
        boolean check = false;
        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("fabric_test");
            contract = network.getContract("roadAsset");
            byte[] updateDriverAsset = contract.evaluateTransaction("driverAssetExists", roadAssetId);
            resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
            check = Boolean.parseBoolean(resp);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     *
     * @param road
     * @return
     */
    public Road createDriverAsset(Road road) {
        String resp = null;
        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("fabric_test");
            contract = network.getContract("roadAsset");
            byte[] createDriverAssetResult = contract.submitTransaction("createRoadAsset", road.getRoadId(),
                    road.getRoadAdminType(),
                    road.getStreetName(),
                    String.valueOf(road.getAdminNumber()),
                    String.valueOf(road.getDistanceTravelledOn()),
                    road.getAdminName(),
                    road.getRoadAdminName(),
                    road.getMunicipality(),
                    road.getState()
                    );


            resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        }
        catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return gson.fromJson(resp, Road.class);
    }


    /**
     * Method to update the driverasset on the blockchain.
     * @param road
     * @return
     */
    public Road updateRoadAsset(Road road) {
        String resp = null;
        try (Gateway gateway = builder.connect()) { //connect with the blockchain through the gateway and its certificates.
            network = gateway.getNetwork("fabric_test");
            contract = network.getContract("roadAsset");
            byte[] createDriverAssetResult = contract.submitTransaction("updateRoadAsset", road.getRoadId(),
                    road.getRoadAdminType(),
                    road.getStreetName(),
                    String.valueOf(road.getAdminNumber()),
                    String.valueOf(road.getDistanceTravelledOn()),
                    road.getAdminName(),
                    road.getRoadAdminName(),
                    road.getMunicipality(),
                    road.getState()
            );

            resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        }
        catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return gson.fromJson(resp, Road.class); //Return the stored info of the car.
    }


    /**
     *
     * @param roadId
     * @return
     */
    public Road readRoadAsset(String roadId) {
        String resp = null;

        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("fabric_test");
            contract = network.getContract("roadAsset");
            byte[] readDriverAsset = contract.evaluateTransaction("readRoadAsset", roadId);
            resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        return gson.fromJson(resp, Road.class);
    }

    /**
     *
     * @return
     */
//    public List<Road> getAllRoadAssets() {
//        String resp = null;
//
//        try (Gateway gateway = builder.connect()) {
//            network = gateway.getNetwork("driverassets");
//            contract = network.getContract("basic");
//            byte[] updateDriverAsset = contract.evaluateTransaction("getAll");
//            resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
//        } catch (ContractException e) {
//            e.printStackTrace();
//        }
//        return gson.fromJson(resp, new TypeToken<List<Road>>(){}.getType());
//    }
}
