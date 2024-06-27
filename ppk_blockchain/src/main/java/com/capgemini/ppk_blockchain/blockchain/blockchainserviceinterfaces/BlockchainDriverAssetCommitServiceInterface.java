package com.capgemini.ppk_blockchain.blockchain.blockchainserviceinterfaces;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;

public interface BlockchainDriverAssetCommitServiceInterface extends BlockchainCommitServiceInterface {

    public boolean updateDriverAsset() throws CommitException, GatewayException;

}
