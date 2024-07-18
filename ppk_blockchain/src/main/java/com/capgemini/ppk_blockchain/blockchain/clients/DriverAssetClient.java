package com.capgemini.ppk_blockchain.blockchain.clients;

import com.capgemini.ppk_blockchain.blockchain.clientinterfaces.DriverAssetClientInterface;
import com.capgemini.ppk_blockchain.blockchain.config.BlockchainConfig;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.util.PrettyJsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

public class DriverAssetClient  implements DriverAssetClientInterface {

    private final String CHAINCODE_NAME = "driverassets";
    private final boolean TRANSACTION_SUCCES = true;
    private Contract contract;
    Genson genson;
    PrettyJsonUtil prettyJsonUtil;

    public DriverAssetClient() throws CertificateException, IOException, InvalidKeyException {
        BlockchainConfig blockchainConfig = new BlockchainConfig(CHAINCODE_NAME);

        this.contract = blockchainConfig.getContract();
        genson = new Genson();
        this.prettyJsonUtil = new PrettyJsonUtil();
    }

    @Override
    public boolean createDriverAsset(String driverId, String licensePlate, String emissionType, String brand) {
        System.out.println(driverId);
        System.out.println(licensePlate);
        System.out.println(emissionType);
        System.out.println(brand);
        try {
           contract.submitTransaction("createDriverAsset", driverId, licensePlate, emissionType, brand);
        } catch (EndorseException | CommitException | CommitStatusException | SubmitException e) {
            throw new RuntimeException(e);
        }
        return TRANSACTION_SUCCES;
    }

    @Override
    public boolean deleteDriverAsset(String driverId) {

        try {
            contract.submitTransaction("deleteDriverAsset", driverId);
        } catch (EndorseException | CommitException | CommitStatusException | SubmitException e) {
            throw new RuntimeException(e);
        }
        return TRANSACTION_SUCCES;
    }

    @Override
    public boolean updateDriverAsset(DriverAsset driverAsset) throws GatewayException, CommitException {

        System.out.println("Hier gekomen");
        System.out.println(driverAsset);
        if (!this.checkForAssetExistence(driverAsset.getDriverAssetId())) {
            this.createDriverAsset(driverAsset.getDriverAssetId(), driverAsset.getLicensePlate(), driverAsset.getEmissionType(), driverAsset.getBrand());
        }
        contract.submitTransaction("updateDriverAsset", //Name of the method on the chaincode.
                driverAsset.getDriverAssetId(), //Identifying info about the driver, in this case we use the licenseplate of the first car send.
                Arrays.toString(driverAsset.getDrivenKilometersOnRoad()),
                String.valueOf(driverAsset.getRideCosts())); //Because the chaincode prefers receiving string, we toString the object.
        //We can then deserialize it on the chaincode to perform operations.

        return TRANSACTION_SUCCES;
    }

    @Override
    public DriverAsset readDriverAsset(String driverAssetId) throws GatewayException {
        String resp = null;
        byte[] readDriverAsset = contract.evaluateTransaction("readDriverAsset", driverAssetId);
        resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        DriverAsset test = genson.deserialize(resp, DriverAsset.class);
        System.out.println(test);
        return genson.deserialize(resp, DriverAsset.class);
    }

    @Override
    public boolean checkForAssetExistence(String assetId) throws GatewayException {
        String resp = null;
        boolean check = false;

        byte[] updateDriverAsset = null;
        try {
            updateDriverAsset = contract.evaluateTransaction("driverAssetExists", assetId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
        check = Boolean.parseBoolean(resp);

        return check;
    }

    @Override
    public List<DriverAsset> retrieveAllAssets() throws GatewayException {
        String resp = null;

        byte[] updateDriverAsset = contract.evaluateTransaction("GetAllAssets");
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
        System.out.println(resp);
        return genson.deserialize(resp, new GenericType<List<DriverAsset>>() {});
    }

    @Override
    public List<DriverAsset> getHistoryForDriverAsset(String driverId) throws GatewayException {
        String resp = null;

        byte[] updateDriverAsset = contract.evaluateTransaction("getHistoryForDriverAsset", driverId);
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
        System.out.println(resp);

        return genson.deserialize(resp, new GenericType<List<DriverAsset>>() {});
    }
}
