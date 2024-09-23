package com.bookride.chalo.dto;

import com.bookride.chalo.entities.enums.PaymentMethod;
import com.bookride.chalo.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RideDto {

    private Long id;

    private PointDto pickupLocation;

    private PointDto dropOffLocation;

    private RiderDto rider;

    private DriverDto driver;

    private RideStatus rideStatus;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdTime;

    private Double fare;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String otp;
}
