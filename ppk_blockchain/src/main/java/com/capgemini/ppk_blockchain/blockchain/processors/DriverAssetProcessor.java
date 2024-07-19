package com.capgemini.ppk_blockchain.blockchain.processors;

import com.capgemini.ppk_blockchain.blockchain.clients.DriverAssetClient;
import com.capgemini.ppk_blockchain.blockchain.model.DriverAsset;
import com.capgemini.ppk_blockchain.blockchain.services.EncryptionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DriverAssetProcessor {

    private final EncryptionService encryptionService;
    private final DriverAssetClient driverAssetClient;

    public DriverAssetProcessor(EncryptionService encryptionService, DriverAssetClient driverAssetClient) {
        this.encryptionService = encryptionService;
        this.driverAssetClient = driverAssetClient;
    }

    public List<DriverAsset> retrieveHistory(String driverAssetId) throws Exception {
        List<DriverAsset> driverAssetList = this.driverAssetClient.getHistoryForDriverAsset(
                this.encryptionService.encrypt(driverAssetId)
        );
        driverAssetList.remove(driverAssetList.size() - 1);
        driverAssetList = driverAssetList.stream().peek(obj -> {
            try {
                System.out.println(obj.toString());
                obj.setDriverAssetId(this.encryptionService.decrypt(obj.getDriverAssetId()));
                obj.setLicensePlate(this.encryptionService.decrypt(obj.getLicensePlate()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        subtractValues(driverAssetList);

        return driverAssetList;
    }

    private void subtractValues(List<DriverAsset> driverAssetList) {
        for (int i = driverAssetList.size() - 1; i > 0; i--) {


            DriverAsset current = driverAssetList.get(i);
            DriverAsset previous = driverAssetList.get(i - 1);

//             Subtract rideCosts
            BigDecimal currentRideCosts = BigDecimal.valueOf(current.getRideCosts());
            BigDecimal previousRideCosts = BigDecimal.valueOf(previous.getRideCosts());
            double rideCostDifference = previousRideCosts.subtract(currentRideCosts)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            previous.setRideCosts(rideCostDifference);

            // Subtract drivenKilometersOnRoad
            double[] currentKilometers = current.getDrivenKilometersOnRoad();
            double[] previousKilometers = previous.getDrivenKilometersOnRoad();
            double[] kilometerDifferences = new double[5];

            if(currentKilometers == null || previousKilometers == null) {
                continue;
            }
            for (int j = 0; j < currentKilometers.length; j++) {
                BigDecimal prevKm = BigDecimal.valueOf(previousKilometers[j]);
                BigDecimal currentKm = BigDecimal.valueOf(currentKilometers[j]);

                kilometerDifferences[j] = prevKm.subtract(currentKm)
                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            previous.setDrivenKilometersOnRoad(kilometerDifferences);
        }
    }
}
