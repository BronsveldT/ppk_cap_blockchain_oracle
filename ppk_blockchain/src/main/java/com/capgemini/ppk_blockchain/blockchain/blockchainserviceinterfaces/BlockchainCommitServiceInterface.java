package com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.SubmitException;

public interface BlockchainCommitServiceInterface {

    public boolean createAsset(String assetId);

    public boolean createAsset() throws EndorseException, CommitException, SubmitException, CommitStatusException;
    public boolean deleteAsset(String assetId);

}
