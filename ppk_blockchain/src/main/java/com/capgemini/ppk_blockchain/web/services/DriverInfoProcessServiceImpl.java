package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.web.interfaces.DriverInfoProcessService;
import com.capgemini.ppk_blockchain.web.models.CarInfo;
import com.capgemini.ppk_blockchain.web.models.OpenstreetMap;
import com.capgemini.ppk_blockchain.web.models.OpenstreetMapRequest;
import com.capgemini.ppk_blockchain.web.models.RoadInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class DriverInfoProcessServiceImpl implements DriverInfoProcessService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient.Builder openStreetMapClient;

    public DriverInfoProcessServiceImpl(WebClient.Builder openStreetMapClient) {
        this.openStreetMapClient = openStreetMapClient;
    }


    @Override
    public Boolean createCarAsset(String carId) {
        return null;
    }

    /**
     * Processes a single ride of the user. Makes use of the openstreetmap reverse geocoding in the function
     * {@link #reverseGeoCodingOpenStreetMap()}.
     * See openstreetmap documentation: https://nominatim.org/release-docs/latest/api/Reverse/
     *
     * @param carInfo
     * @return
     */
    @Override
    public Boolean processDriverInformation(CarInfo carInfo) {
        //First check the reverse geocoding.
        for (RoadInformation roadinformation: carInfo.roadInformation
             ) {
            try {
                OpenstreetMap openstreetMap = reverseGeoCodingOpenStreetMap(roadinformation.getLatitude(), roadinformation.getLongitude());
                Thread.sleep(1500);
                
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Driver information processed");
//        reverseGeoCodingOpenStreetMap(carInfo.roadInformation.get(0).latitude, carInfo.roadInformation.get(0).longitude);
        return null;
    }

    private OpenstreetMap reverseGeoCodingOpenStreetMap(double lat, double lon) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon
                + "&format=json&addressdetails=1&zoom=17";
        OpenstreetMapRequest openstreetMapRequest = new OpenstreetMapRequest(lat, lon);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenstreetMap> resp = restTemplate.getForEntity(url, OpenstreetMap.class);
        System.out.println(resp);
        if (resp.getStatusCode() == HttpStatus.OK) {
            return resp.getBody();
        } else {
            // Handle errors
            return null;
        }
    }
}
