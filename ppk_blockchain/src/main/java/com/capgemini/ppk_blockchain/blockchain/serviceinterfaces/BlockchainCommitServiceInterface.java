package com.capgemini.ppk_blockchain.blockchain.serviceinterfaces;

public interface BlockchainCommitServiceInterface {

    public boolean createAsset(String assetId);

    public boolean deleteAsset(String assetId);

}
