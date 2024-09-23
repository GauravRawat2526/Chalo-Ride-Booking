package com.bookride.chalo.strategies;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

     List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
