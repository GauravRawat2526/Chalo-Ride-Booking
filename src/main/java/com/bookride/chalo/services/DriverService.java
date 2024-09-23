package com.bookride.chalo.services;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RideDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId , String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId , Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverStatus(Driver driverId , boolean available);

    Driver createNewDriver(Driver driver);
}
