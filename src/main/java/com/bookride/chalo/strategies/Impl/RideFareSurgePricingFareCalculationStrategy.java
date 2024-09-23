package com.bookride.chalo.strategies.Impl;

import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.services.DistanceService;
import com.bookride.chalo.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance =  distanceService.calculateDistance(rideRequest.getPickupLocation() , rideRequest.getDropOffLocation());
        return distance * RIDE_MULTIPLIER * SURGE_FACTOR;
    }
}
