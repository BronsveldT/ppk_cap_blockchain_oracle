package com.capgemini.ppk_blockchain.blockchain.credentials;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Set;

public class RegisterUser {
    private RegisterUser() {

    }

    public static void enroll() {
        Properties props = new Properties();
        props.put("pemFile",
                "../../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
        props.put("allowAllHostNames", "true");

        try {
            HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
            caClient.setCryptoSuite(cryptoSuite);

            Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

            if (wallet.get("appUser") != null) {
                System.out.println("An identity for the \"appUser\" already exists in the wallet");
                return;
            }

            X509Identity adminIdentity = (X509Identity) wallet.get("admin");

            if (adminIdentity == null) {
                System.out.println("\"admin\" needs to be enrolled and added to wallet first");
                return;
            }

            User admin = new User() {

                @Override
                public String getName() {
                    return "admin";
                }

                @Override
                public Set<String> getRoles() {
                    return null;
                }

                @Override
                public String getAccount() {
                    return null;
                }

                @Override
                public String getAffiliation() {
                    return "org1.department1";
                }

                @Override
                public Enrollment getEnrollment() {
                    return new Enrollment() {
                        @Override
                        public PrivateKey getKey() {
                            return adminIdentity.getPrivateKey();
                        }

                        @Override
                        public String getCert() {
                            return Identities.toPemString(adminIdentity.getCertificate());
                        }
                    };
                }

                @Override
                public String getMspId() {
                    return "Org1MSP";
                }
            };

            RegistrationRequest registrationRequest = new RegistrationRequest("appUser");
            registrationRequest.setAffiliation("org1.department1");
            registrationRequest.setEnrollmentID("appUser");
            String enrollmentSecret = caClient.register(registrationRequest, admin);
            Enrollment enrollment = caClient.enroll("appUser", enrollmentSecret);
            Identity user = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put("appUser", user);

            System.out.println("Succesfully enrolled appUser");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
