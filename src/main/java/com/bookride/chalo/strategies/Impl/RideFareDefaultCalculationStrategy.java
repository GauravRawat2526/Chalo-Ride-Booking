package com.bookride.chalo.strategies.Impl;

import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.services.DistanceService;
import com.bookride.chalo.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Primary
public class RideFareDefaultCalculationStrategy  implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {

        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation() , rideRequest.getDropOffLocation());
        return distance * RIDE_MULTIPLIER;
    }
}
