package com.bookride.chalo.services.impl;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Rating;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.Rider;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.exceptions.RuntimeConflictException;
import com.bookride.chalo.repositories.DriverRepository;
import com.bookride.chalo.repositories.RatingRepository;
import com.bookride.chalo.repositories.RiderRepository;
import com.bookride.chalo.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id :" + ride.getId()));

        if(ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has already been rated , cannot rate again");
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);
        driver.setRating(newRating);
       Driver savedDriver = driverRepository.save(driver);
       return modelMapper.map(savedDriver , DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id :" + ride.getId()));

        if(ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider has already been rated , cannot rate again");

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);
        rider.setRating(newRating);
        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider , RiderDto.class);
    }

    @Override
    public Rating createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();

        return ratingRepository.save(rating);
    }
}
