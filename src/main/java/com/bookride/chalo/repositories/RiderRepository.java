package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.Rider;
import com.bookride.chalo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
