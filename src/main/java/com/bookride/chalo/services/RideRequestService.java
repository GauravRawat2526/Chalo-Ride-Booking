package com.bookride.chalo.services;

import com.bookride.chalo.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long id);

    void update(RideRequest rideRequest);
}
