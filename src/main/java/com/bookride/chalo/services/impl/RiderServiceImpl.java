package com.bookride.chalo.services.impl;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RideDto;
import com.bookride.chalo.dto.RideRequestDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.*;
import com.bookride.chalo.entities.enums.RideRequestStatus;
import com.bookride.chalo.entities.enums.RideStatus;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.repositories.RideRequestRepository;
import com.bookride.chalo.repositories.RiderRepository;
import com.bookride.chalo.services.DriverService;
import com.bookride.chalo.services.RatingService;
import com.bookride.chalo.services.RideService;
import com.bookride.chalo.services.RiderService;
import com.bookride.chalo.strategies.RiderStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper mapper;
    private final RiderStrategyManager riderStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = mapper.map(rideRequestDto , RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        double fare = riderStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        rideRequest.setRider(getCurrentRider());
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = riderStrategyManager.driverMatchingStrategy(getCurrentRider().getRating()).findMatchingDrivers(rideRequest);

        // TODO: send notification to all the drivers about this ride request
         return mapper.map(savedRideRequest , RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);

        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with id " + ride.getId());
        }

        if(!(ride.getRideStatus().equals(RideStatus.CONFIRMED))){
            throw new RuntimeException("Rider cannot be cancelled , invalid status " + ride.getRideStatus());
        }
        driverService.updateDriverStatus(ride.getDriver() , true);

        Ride savedRide = rideService.updateRideStatus(ride , RideStatus.CANCELLED);

        return mapper.map(savedRide , RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto reteDriver(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not the owner of this ride");

        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ENDED hence cannot start rating, status: " + ride.getRideStatus());
        }

        return ratingService.rateDriver(ride , rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return mapper.map(rider , RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider, pageRequest).map(ride -> mapper.map(ride , RideDto.class));
    }

    @Override
    public Rider createRiderFromUser(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // TODO : implement Spring Security
        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Rider not associated with user  with id: " + user.getId()));
    }


}
