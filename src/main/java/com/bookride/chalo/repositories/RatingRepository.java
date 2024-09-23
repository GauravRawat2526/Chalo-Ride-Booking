package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.Rating;
import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating , Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);
    Optional<Rating> findByRide(Ride ride);
}
