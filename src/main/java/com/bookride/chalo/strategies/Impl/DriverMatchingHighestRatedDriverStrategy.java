package com.bookride.chalo.strategies.Impl;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.repositories.DriverRepository;
import com.bookride.chalo.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearestTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
