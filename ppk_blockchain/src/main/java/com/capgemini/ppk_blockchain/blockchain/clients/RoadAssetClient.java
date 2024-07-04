package com.capgemini.ppk_blockchain.blockchain.clients;

import com.capgemini.ppk_blockchain.blockchain.clientinterfaces.RoadAssetClientInterface;
import com.capgemini.ppk_blockchain.blockchain.config.BlockchainConfig;
import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.capgemini.ppk_blockchain.blockchain.util.PrettyJsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hyperledger.fabric.client.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;

public class RoadAssetClient implements RoadAssetClientInterface {


    private final String CHAINCODE_NAME = "basic";
    private Contract contract;
    private final boolean TRANSACTION_SUCCES = true;
    Gson gson;
    PrettyJsonUtil prettyJsonUtil;

    public RoadAssetClient() throws CertificateException, IOException, InvalidKeyException {

        BlockchainConfig blockchainConfig = new BlockchainConfig(CHAINCODE_NAME);

        this.contract = blockchainConfig.getContract();
        gson = new Gson().newBuilder().create();
        this.prettyJsonUtil = new PrettyJsonUtil();
    }

    @Override
    public boolean createRoadAsset(Road road) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        String resp = null;


        contract.submitTransaction("createRoadAsset", road.getRoadId(),
                Objects.requireNonNullElse(road.getRoadAdminType(), "empty"),
                Objects.requireNonNullElse(road.getStreetName(), "Empty"),
                Objects.requireNonNullElse(String.valueOf(road.getAdminNumber()), "Empty"),
                Objects.requireNonNullElse(String.valueOf(road.getDistanceTravelledOn()), "Empty"),
                Objects.requireNonNullElse(road.getAdminName(), "Empty"),
                Objects.requireNonNullElse(road.getRoadAdminName(), "Empty"),
                Objects.requireNonNullElse(road.getMunicipality(), "Empty"),
                Objects.requireNonNullElse(road.getState(), "Empty")
        );
        return TRANSACTION_SUCCES;
    }

    /**
     * Method to update the driverasset on the blockchain.
     *
     * @param road
     * @return
     */
    public boolean updateRoadAsset(Road road) throws EndorseException, CommitException, SubmitException, CommitStatusException {

       contract.submitTransaction("updateRoadAsset", road.getRoadId(),
                String.valueOf(road.getDistanceTravelledOn())
        );

       return TRANSACTION_SUCCES;
    }

    @Override
    public List<Road> readRoadAssetByName(String roadName) {
        return List.of();
    }


    /**
     * @param roadId
     * @return
     */
    public List<Road> readRoadAsset(String roadId) {
        String resp = null;

        byte[] readDriverAsset = null;
        try {
            readDriverAsset = contract.evaluateTransaction("readRoadAsset", roadId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
        resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        Type listType = new TypeToken<List<Road>>() {
        }.getType();
        return gson.fromJson(resp, listType);
    }

    /**
     * @param municipality
     * @return
     */
    public List<Road> retrieveRoadsByMunicipality(String municipality) {
        String resp = null;

        byte[] readDriverAsset = null;
        try {
            readDriverAsset = contract.evaluateTransaction("readRoadByMunicipality", municipality);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
        resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        Type listType = new TypeToken<List<Road>>() {
        }.getType();
        return gson.fromJson(resp, listType);
    }

    @Override
    public boolean checkForAssetExistence(String assetId) throws GatewayException {
        String resp = null;
        boolean check = false;
        var updateDriverAsset = contract.evaluateTransaction("roadAssetExists", assetId);

        System.out.println("*** Result:" + prettyJsonUtil.prettyJson(updateDriverAsset));
        resp = prettyJsonUtil.prettyJson(updateDriverAsset);
        if (resp.equalsIgnoreCase("true") || resp.equalsIgnoreCase("false")) {
            check = Boolean.parseBoolean(resp);
        } else {
            System.out.println("Gooi een exception");
        }
        System.out.println("Check is: " + check);
        return check;
    }

    @Override
    public List<Road> retrieveAllAssets() throws GatewayException {
        String resp = null;

        byte[] updateDriverAsset = contract.evaluateTransaction("getAll");
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);

        return gson.fromJson(resp, new TypeToken<List<Road>>() {
        }.getType());
    }
}
