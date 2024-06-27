package com.capgemini.ppk_blockchain.blockchain.serviceinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;

public interface BlockchainDriverAssetCommitServiceInterface extends BlockchainCommitServiceInterface {

    public boolean updateDriverAsset(DriverAsset driverAsset);


}
