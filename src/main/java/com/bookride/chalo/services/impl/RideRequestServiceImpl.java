package com.bookride.chalo.services.impl;

import com.bookride.chalo.entities.RideRequest;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.repositories.RideRequestRepository;
import com.bookride.chalo.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long id) {
        return rideRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RideRequest not found for id :" + id));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found for id " + rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
