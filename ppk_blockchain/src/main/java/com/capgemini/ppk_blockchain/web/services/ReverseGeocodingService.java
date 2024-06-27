package com.capgemini.ppk_blockchain.web.services;

import com.capgemini.ppk_blockchain.web.restmodels.OpenstreetMap;
import com.capgemini.ppk_blockchain.web.restmodels.OpenstreetMapRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReverseGeocodingService {

    private final RestTemplate restTemplate;

    public ReverseGeocodingService() {
        this.restTemplate = new RestTemplate();
    }

    public OpenstreetMap reverseGeoCodingOpenStreetMap(double lat, double lon) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon
                + "&format=json&addressdetails=1&zoom=17";
        ResponseEntity<OpenstreetMap> resp = restTemplate.getForEntity(url, OpenstreetMap.class);
        if (resp.getStatusCode() == HttpStatus.OK) {
            return resp.getBody();
        } else {
            // Handle errors
            return null;
        }
    }
}
