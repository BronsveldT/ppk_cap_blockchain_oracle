package com.capgemini.ppk_blockchain.blockchain.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class BlockchainDriverAssetController {

    Network network;
    Gateway.Builder builder;
    Contract contract;
    Gson gson;
    long seconds = 20;

    public BlockchainDriverAssetController() throws IOException {
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
     * @param driverAssetId
     * @return
     */
    public boolean checkForDriverAssetExistence(String driverAssetId) {
        String resp = null;
        boolean check = false;
        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("driverassets");
            contract = network.getContract("basic");
            byte[] updateDriverAsset = contract.evaluateTransaction("driverAssetExists", driverAssetId);
            resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
            check = Boolean.parseBoolean(resp);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     *
     * @param driverId
     * @return
     */
    public DriverAsset createDriverAsset(String driverId) {
        String resp = null;
        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("driverassets");
            contract = network.getContract("basic");
            byte[] createDriverAssetResult = contract.submitTransaction("createDriverAsset", driverId);
            resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        }
        catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return gson.fromJson(resp, DriverAsset.class);
    }

    /**
     * Method to update the driverasset on the blockchain.
     * @param driverAsset
     * @return
     */
    public DriverAsset updateDriverAsset(DriverAsset driverAsset) {
        String resp = null;
        try (Gateway gateway = builder.connect()) { //connect with the blockchain through the gateway and its certificates.
            network = gateway.getNetwork("driverassets");
            contract = network.getContract("basic");
            byte[] createDriverAssetResult = contract.submitTransaction("updateDriverAsset", //Name of the method on the chaincode.
                    driverAsset.getDriverAssetId(), //Identifying info about the driver, in this case we use the licenseplate of the first car send.
                    driverAsset.toString()); //Because the chaincode prefers receiving string, we toString the object.
            //We can then deserialize it on the chaincode to perform operations.
            resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        }
        catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return gson.fromJson(resp, DriverAsset.class); //Return the stored info of the car.
    }

    /**
     *
     * @param driverAssetId
     * @return
     */
    public DriverAsset readDriverAsset(String driverAssetId) {
        String resp = null;

        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("driverassets");
            contract = network.getContract("basic");
            byte[] readDriverAsset = contract.evaluateTransaction("retrieveAsset", driverAssetId);
            resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        return gson.fromJson(resp, DriverAsset.class);
    }

    /**
     *
     * @return
     */
    public List<DriverAsset> getAllDriverAssets() {
        String resp = null;

        try (Gateway gateway = builder.connect()) {
            network = gateway.getNetwork("driverassets");
            contract = network.getContract("basic");
            byte[] updateDriverAsset = contract.evaluateTransaction("getAll");
            resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return gson.fromJson(resp, new TypeToken<List<DriverAsset>>(){}.getType());
    }
}
