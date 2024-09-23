package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest , Long> {
}
