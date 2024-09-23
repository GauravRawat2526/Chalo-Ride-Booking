package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.Payment;
import com.bookride.chalo.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment , Long> {

    Optional<Payment> findByRide(Ride ride);
}
