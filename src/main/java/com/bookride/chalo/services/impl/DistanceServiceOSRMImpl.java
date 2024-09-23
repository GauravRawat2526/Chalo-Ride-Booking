package com.bookride.chalo.services.impl;

import com.bookride.chalo.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";
    @Override
    public double calculateDistance(Point src, Point des) {

        try{
        String uri = src.getX() + "," + src.getY() + ";" + des.getX() + "," + des.getY();
        OSRMResponseDto osrmResponseDto = RestClient.builder()
                .baseUrl(OSRM_API_BASE_URL)
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .body(OSRMResponseDto.class);
        return osrmResponseDto.getRoutes().getFirst().getDistance() / 1000;
        }
        catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM " + e.getMessage());
        }
    }
}

@Data
class OSRMResponseDto{
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute{
    private Double distance;
}