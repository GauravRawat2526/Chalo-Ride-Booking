package com.bookride.chalo.services.impl;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RideDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.entities.User;
import com.bookride.chalo.entities.enums.RideRequestStatus;
import com.bookride.chalo.entities.enums.RideStatus;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.repositories.DriverRepository;
import com.bookride.chalo.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;
    private final EmailSenderService emailSenderService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest cannot be accepted , status is "+ rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }


        Driver savedDriver = updateDriverStatus(currentDriver , false);

        Ride ride = rideService.createNewRide(rideRequest , savedDriver);
        emailSenderService.sendEmail(ride.getRider().getUser().getEmail() , "Ride OTP" , ride.getOtp());
        ride.setOtp(null);
        return modelMapper.map(ride , RideDto.class);
    }

    @Transactional
    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride =  rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!(ride.getRideStatus().equals(RideStatus.CONFIRMED))){
            throw new RuntimeException("Driver cannot cancelled the confirmed ride");
        }
        rideService.updateRideStatus(ride , RideStatus.CANCELLED);
        updateDriverStatus(driver , true);
        return modelMapper.map(ride , RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId ,String otp) {

        Ride ride =  rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: " + ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid, otp: " + otp);
        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride , RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        return modelMapper.map(savedRide , RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId ) {

        Ride ride =  rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not ONGOING hence cannot be started, status: " + ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride , RideStatus.ENDED);
        updateDriverStatus(driver , true);
        paymentService.processPayment(ride);
        return modelMapper.map(savedRide , RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not the owner of this ride");

        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ENDED hence cannot start rating, status: " + ride.getRideStatus());
        }
        return ratingService.rateRider(ride , rating);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver driver = getCurrentDriver();
        return modelMapper.map(driver , DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver driver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(driver , pageRequest).map(ride -> modelMapper.map(ride , RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user  with id " + user.getId()));
    }

    @Override
    public Driver updateDriverStatus(Driver  driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
