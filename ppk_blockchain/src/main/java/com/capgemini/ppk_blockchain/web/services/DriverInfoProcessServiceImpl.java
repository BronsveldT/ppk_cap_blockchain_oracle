package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.blockchain.interfaceimpl.BlockchainProcessServiceImpl;
import com.capgemini.ppk_blockchain.web.db.models.ReverseRoads;
import com.capgemini.ppk_blockchain.web.db.models.Wegen;
import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoProcessService;
import com.capgemini.ppk_blockchain.web.repositories.ReverseRoadRepository;
import com.capgemini.ppk_blockchain.web.repositories.WegenRepository;
import com.capgemini.ppk_blockchain.web.restmodels.*;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.HashMap;

@Service
public class DriverInfoProcessServiceImpl implements DriverInfoProcessService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    final
    WegenRepository wegenRepository;
    final
    ReverseRoadRepository reverseRoadRepository;
    final
    BlockchainProcessServiceImpl blockchainService;

    HashMap<String, Integer> roadsNotInDataset;
    public DriverInfoProcessServiceImpl(WegenRepository wegenRepository, ReverseRoadRepository reverseRoadRepository, BlockchainProcessServiceImpl blockchainService) {
        this.wegenRepository = wegenRepository;
        this.reverseRoadRepository = reverseRoadRepository;
        roadsNotInDataset = new HashMap<>();
        roadsNotInDataset.put("Doctor J.M. den Uylweg", 2);
        roadsNotInDataset.put("Thorbeckeweg", 2);
        roadsNotInDataset.put("Kolkweg", 2);
        roadsNotInDataset.put("Oostzanerdijk", 3);
        roadsNotInDataset.put("Coentunnelweg", 1);
        roadsNotInDataset.put("Ringweg-Noord", 1);
        roadsNotInDataset.put("Westrandweg", 1); //lat='52.398605073551764', lon='4.846242937148632' lat='52.38723356252401', lon='4.768465784090613
        // lat='52.39316003788911', lon='4.778848147911693
        roadsNotInDataset.put("Ringweg-West", 1); //52.40618094325841', lon='4.854952033354235
        roadsNotInDataset.put("Rijksweg A5", 1); //lat='52.294619944308984', lon='4.731161495112549'
        roadsNotInDataset.put("Rijksweg A4", 1); //lat='52.294619944308984', lon='4.731161495112549'
        roadsNotInDataset.put("A5", 1); //lat='52.294619944308984', lon='4.731161495112549'
        roadsNotInDataset.put("A4", 1); //lat='52.24794488633926', lon='4.668793080778405'
        roadsNotInDataset.put("N468", 2); //lat='52.24794488633926', lon='4.668793080778405'
        roadsNotInDataset.put("N223", 2); //lat='51.98585196514408', lon='4.28407999101316',  lat='51.97391438113616', lon='4.255503797799819',
        roadsNotInDataset.put("Klaas Engelbrechtsweg", 3); //lat='51.994826901946325', lon='4.316035831134797',
        roadsNotInDataset.put("Burgemeester van der Goeslaan", 3); //lat='51.97867085883025', lon='4.268605020817659
        roadsNotInDataset.put("Woudseweg", 2); //lat='51.989923882854654', lon='4.2887367524719755'
        roadsNotInDataset.put("Oude Liermolenweg", 2); //lat='51.97852597358414', lon=4.268064963395825
        this.blockchainService = blockchainService;
    }

    @Override
    public Boolean createCarAsset(String carId) {
        return null;
    }

    /**
     * Steps:
     *
     * 1: Loop for the roadInformations we got in the carInfo DONE
     * 2: Check if we got the lat-lon combination in the local database DONE
     * 3: If so -> add the values to the driverAsset. DONE
     * 4: If not -> Make a remote request to the nominatim server to reverse geolocate. DONE
     * 5: Retrieve the roadcategory and admintype from the local database. DONE
     * 6: Add values to the driverasset. DONE
     */
    /**
     * Processes a single ride of the user. Makes use of the openstreetmap reverse geocoding in the function
     * {@link #()}.
     * See openstreetmap documentation: https://nominatim.org/release-docs/latest/api/Reverse/
     *
     * @param carInfo
     * @return
     */
    @Override
    public double processDriverInformation(CarInfo carInfo) throws CommitException, GatewayException {
        this.blockchainService.addCarInfoToDriverAsset(carInfo.getKenteken(), carInfo.getKenteken(),
                carInfo.getMerk(), carInfo.getEmissieType());
        int skippedRoads = 0;
        for (RoadInformation roadinformation: carInfo.getRoadInformation()
             ) {

            ReverseRoads reverseRoads = reverseRoadRepository.findByLatAndLon(roadinformation.getLatitude(), roadinformation.getLongitude());
            if(reverseRoads != null) { //In case we already got the lat and lon combination in the local db stored, there is no need to
                                        //make a request to nominatim.
                this.blockchainService.addTravelInformationToDriverAsset(reverseRoads.getRoadCategory(),reverseRoads.getStreetName(), roadinformation);
                this.blockchainService.addRoadData(
                        reverseRoads.getRoadAdminType(),
                        reverseRoads.getStreetName(),
                        reverseRoads.getAdminNumber(),
                        reverseRoads.getAdminName(),
                        reverseRoads.getRoadAdminName(),
                        roadinformation.getDistanceToPrev()
                );
            } else {
                OpenstreetMap openstreetMap = reverseGeoCodingOpenStreetMap(roadinformation.getLatitude(), roadinformation.getLongitude());
                Address address;
                if (openstreetMap != null) {
                    address = openstreetMap.getAddress();
                    if(roadsNotInDataset.containsKey(address.getRoad())) {
                        this.blockchainService.addTravelInformationToDriverAsset(roadsNotInDataset.get(address.getRoad()), address.getRoad(),
                                roadinformation);
                        this.blockchainService.addRoadData(
                                roadsNotInDataset.get(address.getRoad()), //For example 2 gets passed here.
                                address.getRoad(),
                                address.getMunicipality(), //For example Zaanstad
                                address.getState(), //For example Noord-Holland
                                roadinformation.getDistanceToPrev()
                        );
                    } else {
                        Wegen wegen = wegenRepository.findWegenByStreetName(address.getRoad(), address.getTown());
                        if(wegen != null) {
                            System.out.println("Komen we hierzo");
                            System.out.println(wegen);
                            this.blockchainService.addTravelInformationToDriverAsset(wegen.getRoadCategory(), wegen.getStreetName(), roadinformation);
                            reverseRoads = new ReverseRoads(
                                    roadinformation.getLatitude(),
                                    roadinformation.getLongitude(),
                                    wegen.getRoadCategory(),
                                    wegen.getStreetName(),
                                    wegen.getRoadAdminType(),
                                    wegen.getAdminNumber(),
                                    wegen.getAdminName(),
                                    wegen.getAdminName()
                            );
                            this.blockchainService.addRoadData(
                                    reverseRoads.getRoadAdminType(),
                                    reverseRoads.getStreetName(),
                                    reverseRoads.getAdminNumber(),
                                    reverseRoads.getAdminName(),
                                    reverseRoads.getRoadAdminName(),
                                    roadinformation.getDistanceToPrev()
                            );
                            reverseRoadRepository.save(reverseRoads);
                        } else {
                            System.out.println("Skipped this road");
                            System.out.println(openstreetMap);
                            skippedRoads++;
                        }
                    }
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            this.blockchainService.sendDataToBlockchain();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Driver information processed");
        System.out.println(skippedRoads);
        return this.blockchainService.retrieveRideCosts();
    }

    private OpenstreetMap reverseGeoCodingOpenStreetMap(double lat, double lon) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon
                + "&format=json&addressdetails=1&zoom=17";
        OpenstreetMapRequest openstreetMapRequest = new OpenstreetMapRequest(lat, lon);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenstreetMap> resp = restTemplate.getForEntity(url, OpenstreetMap.class);
        if (resp.getStatusCode() == HttpStatus.OK) {
            return resp.getBody();
        } else {
            // Handle errors
            return null;
        }
    }
}
