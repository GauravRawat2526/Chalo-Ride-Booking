package com.bookride.chalo.services;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RideDto;
import com.bookride.chalo.dto.RideRequestDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.Rider;
import com.bookride.chalo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId);


    DriverDto reteDriver(Long rideId , Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createRiderFromUser(User user);

    Rider getCurrentRider();


}
