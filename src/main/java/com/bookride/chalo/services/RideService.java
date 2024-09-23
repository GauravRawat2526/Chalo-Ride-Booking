package com.bookride.chalo.services;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.entities.Rider;
import com.bookride.chalo.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest , Driver driver);

    Ride updateRideStatus(Ride ride , RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider , PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver , PageRequest pageRequest);
}

