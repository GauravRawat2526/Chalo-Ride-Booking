package com.bookride.chalo.repositories;

import com.bookride.chalo.entities.Ride;
import com.bookride.chalo.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {


    Page<Ride> findByRider(Rider rider, PageRequest pageRequest);
}
