package com.bookride.chalo.services.impl;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.entities.Rider;
import com.bookride.chalo.entities.enums.RideRequestStatus;
import com.bookride.chalo.entities.enums.RideStatus;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.repositories.RideRepository;
import com.bookride.chalo.services.RideRequestService;
import com.bookride.chalo.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRequestService rideRequestService;
    private final RideRepository rideRepository;
    private final ModelMapper mapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found for id :" + rideId));
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = mapper.map(rideRequest , Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setId(null);
        ride.setOtp(generateOtp());

        rideRepository.save(ride);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride );
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider , pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return null;
    }

    private String generateOtp(){
        Random random = new Random();
        int otpInt = random.nextInt(10000);
        return String.format("%04d" , otpInt);
    }
}
