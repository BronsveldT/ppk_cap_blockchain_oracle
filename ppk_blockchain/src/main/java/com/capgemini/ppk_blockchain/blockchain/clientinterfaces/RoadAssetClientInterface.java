package com.capgemini.ppk_blockchain.blockchain.clientinterfaces;

import com.capgemini.ppk_blockchain.blockchain.model.Road;
import org.hyperledger.fabric.client.*;

import java.util.List;

public interface RoadAssetClientInterface extends GeneralAssetInterface {

    Road createRoadAsset(Road road) throws EndorseException, CommitException, SubmitException, CommitStatusException;

    Road updateRoadAsset(Road road) throws EndorseException, CommitException, SubmitException, CommitStatusException;

    List<Road> readRoadAssetByName(String roadName);

    List<Road> retrieveRoadsByMunicipality(String municipality);

}
