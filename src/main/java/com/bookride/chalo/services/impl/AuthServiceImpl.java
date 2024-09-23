package com.bookride.chalo.services.impl;

import com.bookride.chalo.dto.DriverDto;
import com.bookride.chalo.dto.SignupDto;
import com.bookride.chalo.dto.UserDto;
import com.bookride.chalo.entities.Driver;
import com.bookride.chalo.entities.User;
import com.bookride.chalo.entities.enums.Role;
import com.bookride.chalo.exceptions.ResourceNotFoundException;
import com.bookride.chalo.exceptions.RuntimeConflictException;
import com.bookride.chalo.repositories.UserRepository;
import com.bookride.chalo.security.JWTService;
import com.bookride.chalo.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final SessionService sessionService;

    @Override
    public String[] login(String email, String password) {
        String[] tokens = new String[2];
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email , password));
        User user = (User)authentication.getPrincipal();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user , refreshToken);
        return new String[] {accessToken , refreshToken};

    }

    @Transactional
    @Override
    public UserDto signup(SignupDto signupDto) {
        userRepository.findByEmail(signupDto.getEmail())
                .ifPresent((user) -> {
                    throw new RuntimeConflictException("User is already registered with email " + user.getEmail());
                });

        User mappedUser = modelMapper.map(signupDto , User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        // create user related entities
        riderService.createRiderFromUser(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser , UserDto.class);
    }

    @Transactional
    @Override
    public DriverDto onboardNewDriver(Long userId , String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id " + userId));
        if(user.getRoles().contains(Role.DRIVER)) throw new RuntimeConflictException("user with id " + userId + "is already a driver");
        Driver driver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        return modelMapper.map(driverService.createNewDriver(driver) , DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("user not found for id" + userId));
        return jwtService.generateToken(user);
    }

    @Override
    public void logout(String refreshToken) {
        sessionService.deleteSession(refreshToken);
    }
}
