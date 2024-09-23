package com.bookride.chalo.services;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.SignupDto;
import com.bookride.chalo.dto.UserDto;

public interface AuthService {

    String[] login(String email , String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId , String vehicleId);

    String refreshToken(String refreshToken);

    void logout(String refreshToken);
}
