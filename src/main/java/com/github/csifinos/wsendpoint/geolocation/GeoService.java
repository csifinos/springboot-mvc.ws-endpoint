package com.github.csifinos.wsendpoint.geolocation;

import com.ip2location.IP2Location;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeoService {

    private final IP2Location ip2location;

    public GeoService() {
        String binPath = "src/main/resources/geo/IP2LOCATION-LITE-DB3.BIN";
        this.ip2location = new IP2Location();

        try {
            this.ip2location.Open(binPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to open IP2Location database at " + binPath, e);
        }
    }

    public Optional<String> getCityByIp(String ip){
        try {
            return Optional.of(ip2location.IPQuery(ip).getCity());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
