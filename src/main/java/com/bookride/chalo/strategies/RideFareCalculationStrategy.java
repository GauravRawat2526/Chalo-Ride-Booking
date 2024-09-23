package com.bookride.chalo.strategies;

import com.bookride.chalo.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_MULTIPLIER = 10;
    double calculateFare(RideRequest rideRequest);
}
