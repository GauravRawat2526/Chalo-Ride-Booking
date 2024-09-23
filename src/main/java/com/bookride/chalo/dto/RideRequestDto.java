package com.bookride.chalo.dto;

import com.bookride.chalo.entities.enums.PaymentMethod;
import com.bookride.chalo.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {

    private Long id;

    private PointDto pickupLocation;

    private PointDto dropOffLocation;

    private RiderDto rider;

    private RideRequestStatus rideRequestStatus;

    private PaymentMethod paymentMethod;

    private String fare;
}
