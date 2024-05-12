package com.capgemini.ppk_blockchain.blockchain.controllers;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BlockchainDriverAssetController {


    Gson gson;
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "capgemini");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "roadAsset");
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

    public BlockchainDriverAssetController() throws IOException, CertificateException, InvalidKeyException {

        // The gRPC client connection should be shared by all Gateway connections to
        // this endpoint.
        var channel = newGrpcConnection();

        var builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));
        try (var gateway = builder.connect()) {
            this.start(gateway);
        }
        gson = new Gson().newBuilder().create();
    }

    private void start(final Gateway gateway) {
        var network = gateway.getNetwork(CHANNEL_NAME);

        // Get the smart contract from the network.
        contract = network.getContract(CHAINCODE_NAME);
    }


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

    /**
     * @param driverAssetId
     * @return
     */
    public boolean checkForDriverAssetExistence(String driverAssetId) {
        String resp = null;
        boolean check = false;

        byte[] updateDriverAsset = null;
        try {
            updateDriverAsset = contract.evaluateTransaction("driverAssetExists", driverAssetId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);
        check = Boolean.parseBoolean(resp);

        return check;
    }

    /**
     * @param driverId
     * @return
     */
    public DriverAsset createDriverAsset(String driverId) {
        String resp = null;

        byte[] createDriverAssetResult = null;
        try {
            createDriverAssetResult = contract.submitTransaction("createDriverAsset", driverId);
        } catch (EndorseException | CommitException | CommitStatusException | SubmitException e) {
            throw new RuntimeException(e);
        }
        resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);
        return gson.fromJson(resp, DriverAsset.class);
    }

    /**
     * Method to update the driverasset on the blockchain.
     *
     * @param driverAsset
     * @return
     */
    public DriverAsset updateDriverAsset(DriverAsset driverAsset) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        String resp = null;
        if (!this.checkForDriverAssetExistence(driverAsset.getDriverAssetId())) {
            this.createDriverAsset(driverAsset.getDriverAssetId());
        }
        byte[] createDriverAssetResult = contract.submitTransaction("updateDriverAsset", //Name of the method on the chaincode.
        driverAsset.getDriverAssetId(), //Identifying info about the driver, in this case we use the licenseplate of the first car send.
        Arrays.toString(driverAsset.getDrivenKilometersOnRoads())); //Because the chaincode prefers receiving string, we toString the object.
        //We can then deserialize it on the chaincode to perform operations.
        resp = new String(createDriverAssetResult, StandardCharsets.UTF_8);

        return gson.fromJson(resp, DriverAsset.class); //Return the stored info of the car.
    }

    /**
     * @param driverAssetId
     * @return
     */
    public DriverAsset readDriverAsset(String driverAssetId) throws GatewayException {
        String resp = null;


        byte[] readDriverAsset = contract.evaluateTransaction("retrieveAsset", driverAssetId);
        resp = new String(readDriverAsset, StandardCharsets.UTF_8);
        System.out.println("Check dit thomas!");
        System.out.println(resp);
        return gson.fromJson(resp, DriverAsset.class);
    }

    /**
     * @return
     */
    public List<DriverAsset> getAllDriverAssets() throws GatewayException {
        String resp = null;

        byte[] updateDriverAsset = contract.evaluateTransaction("getAll");
        resp = new String(updateDriverAsset, StandardCharsets.UTF_8);

        return gson.fromJson(resp, new TypeToken<List<DriverAsset>>() {
        }.getType());
    }
}
