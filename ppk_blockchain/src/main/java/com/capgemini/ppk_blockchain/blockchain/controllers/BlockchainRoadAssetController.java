package com.capgemini.ppk_blockchain.blockchain.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.SubmitException;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Identity;
import org.hyperledger.fabric.client.identity.Signer;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BlockchainRoadAssetController {
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "capgemini");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "basic");
    private static final String MSP_ID = System.getenv().getOrDefault("MSP_ID", "Org1MSP");

    // Path to crypto materials.
    private static final Path CRYPTO_PATH = Paths.get("../../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com");
    // Path to user certificate.
    private static final Path CERT_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/signcerts"));
    // Path to user private key directory.
    private static final Path KEY_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/keystore"));
    // Path to peer tls certificate.
    private static final Path TLS_CERT_PATH = CRYPTO_PATH.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"));
    // Gateway peer end point.
    private static final String PEER_ENDPOINT = "localhost:7051";
    private static final String OVERRIDE_AUTH = "peer0.org1.example.com";

    private Contract contract;

    Gson gson;


    public BlockchainRoadAssetController() throws IOException, CertificateException, InvalidKeyException {

        // The gRPC client connection should be shared by all Gateway connections to
        // this endpoint.
        var channel = newGrpcConnection();

        var builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));
        try (var gateway = builder.connect()){
            this.start(gateway);
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private void start(final Gateway gateway){
        var network = gateway.getNetwork(CHANNEL_NAME);

        // Get the smart contract from the network.
        contract = network.getContract(CHAINCODE_NAME);
        System.out.println(contract.toString());
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    /**
     *
     * @param roadAssetId
     * @return
     */
    public boolean checkForRoadAssetExistence(String roadAssetId) throws GatewayException {
        String resp = null;
        boolean check = false;
        var updateDriverAsset = contract.evaluateTransaction("roadAssetExists", roadAssetId);

        System.out.println("*** Result:" + prettyJson(updateDriverAsset));

        return check;
    }

    /**
     *
     * @param road
     * @return
     */
    public Road createDriverAsset(Road road) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        String resp = null;


            var createDriverAssetResult = contract.submitTransaction("createRoadAsset", road.getRoadId(),
                    Objects.requireNonNullElse(road.getRoadAdminType(), "empty"),
                    Objects.requireNonNullElse(road.getStreetName(), "Empty"),
                    Objects.requireNonNullElse(String.valueOf(road.getAdminNumber()), "Empty"),
                    Objects.requireNonNullElse(String.valueOf(road.getDistanceTravelledOn()), "Empty"),
                    Objects.requireNonNullElse(road.getAdminName(),"Empty"),
                    Objects.requireNonNullElse(road.getRoadAdminName(), "Empty"),
                    Objects.requireNonNullElse(road.getMunicipality(), "Empty"),
                    Objects.requireNonNullElse(road.getState(), "Empty")
                    );
        System.out.println("Transaction committed successfully");
        return null;
//        resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
//
//        return gson.fromJson(resp, Road.class);
    }


    /**
     * Method to update the driverasset on the blockchain.
     * @param road
     * @return
     */
    public Road updateRoadAsset(Road road) {
        String resp = null;
        byte[] createDriverAssetResult = null;
        try {
            createDriverAssetResult = contract.submitTransaction("updateRoadAsset", road.getRoadId(),
                    road.getRoadAdminType(),
                    road.getStreetName(),
                    String.valueOf(road.getAdminNumber()),
                    String.valueOf(road.getDistanceTravelledOn()),
                    road.getAdminName(),
                    road.getRoadAdminName(),
                    road.getMunicipality(),
                    road.getState()
            );
        } catch (EndorseException | CommitException | CommitStatusException | SubmitException e) {
            throw new RuntimeException(e);
        }

        resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        return gson.fromJson(resp, Road.class); //Return the stored info of the car.
    }


    /**
     *
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
        Type listType = new TypeToken<List<Road>>() {}.getType();
        return gson.fromJson(resp, listType);
    }

    /**
     *
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
        Type listType = new TypeToken<List<Road>>() {}.getType();
        return gson.fromJson(resp, listType);
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

    private static ManagedChannel newGrpcConnection() throws IOException {
        var credentials = TlsChannelCredentials.newBuilder()
                .trustManager(TLS_CERT_PATH.toFile())
                .build();
        return Grpc.newChannelBuilder(PEER_ENDPOINT, credentials)
                .overrideAuthority(OVERRIDE_AUTH)
                .build();
    }

    private static Identity newIdentity() throws IOException, CertificateException {
        try (var certReader = Files.newBufferedReader(getFirstFilePath(CERT_DIR_PATH))) {
            var certificate = Identities.readX509Certificate(certReader);
            return new X509Identity(MSP_ID, certificate);
        }
    }

    private static Signer newSigner() throws IOException, InvalidKeyException {
        try (var keyReader = Files.newBufferedReader(getFirstFilePath(KEY_DIR_PATH))) {
            var privateKey = Identities.readPrivateKey(keyReader);
            return Signers.newPrivateKeySigner(privateKey);
        }
    }

    private static Path getFirstFilePath(Path dirPath) throws IOException {
        try (var keyFiles = Files.list(dirPath)) {
            return keyFiles.findFirst().orElseThrow();
        }
    }

}
