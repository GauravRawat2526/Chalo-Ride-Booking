package com.bookride.chalo.services;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.RiderDto;
import com.bookride.chalo.entities.Rating;
import com.bookride.chalo.entities.Ride;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);
    Rating createNewRating(Ride ride);
}
