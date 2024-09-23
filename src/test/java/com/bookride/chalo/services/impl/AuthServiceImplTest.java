package com.bookride.chalo.services.impl;

import com.bookride.chalo.dto.SignupDto;
import com.bookride.chalo.dto.UserDto;
import com.bookride.chalo.entities.User;
import com.bookride.chalo.entities.enums.Role;
import com.bookride.chalo.exceptions.RuntimeConflictException;
import com.bookride.chalo.repositories.UserRepository;
import com.bookride.chalo.security.JWTService;
import com.bookride.chalo.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private  RiderServiceImpl riderService;

    @Mock
    private  WalletServiceImpl walletService;

    @Mock
    private  DriverServiceImpl driverService;

    @Spy
    private  PasswordEncoder passwordEncoder;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private  AuthenticationManager authenticationManager;

    @Mock
    private  JWTService jwtService;

    @Mock
    private  SessionService sessionService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private SignupDto signupDto;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRoles(Set.of(Role.RIDER));

        signupDto  = new SignupDto();
        signupDto.setEmail("test@gmail.com");
        signupDto.setPassword("password");
    }

    @Test
    void testLogin_whenEmailAndPasswordIsCorrect_thenReturnTokens(){
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

       String[] tokens = authService.login(user.getEmail() , user.getPassword());

       assertThat(tokens).isNotNull();
       assertThat(tokens).hasSize(2);
       assertThat(tokens[0]).isEqualTo("accessToken");
       assertThat(tokens[1]).isEqualTo("refreshToken");
    }

    @Test
    void testSignup_whenNewUserIsRegistering_thenReturnNewUser(){
        //arrange
        when(userRepository.findByEmail(signupDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto newUser = authService.signup(signupDto);

        //assert
        assertThat(newUser).isNotNull();
        assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
        verify(riderService).createRiderFromUser(any(User.class));
        verify(walletService).createNewWallet(any(User.class));
    }

    @Test
    void testSignup_whenExistingUserRegistering_thenThrowException(){
        //arrange
        when(userRepository.findByEmail(signupDto.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(() ->  authService.signup(signupDto)).isInstanceOf(RuntimeConflictException.class);


        verify(userRepository , never()).save(any(User.class));
        verify(riderService , never()).createRiderFromUser(any(User.class));
        verify(walletService , never()).createNewWallet(any(User.class));
    }
}