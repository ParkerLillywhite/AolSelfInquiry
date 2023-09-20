package com.auth;

import com.config.JwtService;
import com.user.User;
import com.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void Setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister_givenValidUserInfo_returnsAuthenticationResponse() {
        RegisterRequest request = RegisterRequest
                .builder()
                .email("boink@boinkos.com")
                .firstname("boink")
                .lastname("boinkos")
                .password("password")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encoded password");
        when(jwtService.generateToken(any(User.class))).thenReturn("generated token");

        AuthenticationService authService = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);
        AuthenticationResponse authResponse = authService.register(request);

        assertNotNull(authResponse);
        assertEquals("generated token", authResponse.getToken());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticate_withValidRequest_returnsGeneratedToken() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("boinkos@boink.com")
                .password("password")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(java.util.Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("generated token");

        AuthenticationService service = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);
        AuthenticationResponse response = service.authenticate(request);

        assertNotNull(response);
        assertEquals("generated token", response.getToken());
    }

    @Test
    public void testAuthenticate_withNonExistentUser_expectsNoSuchElementError() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("crog")
                .password("password")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.empty());

        AuthenticationService service = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        assertThrows(NoSuchElementException.class, () -> {
            service.authenticate(request);
        });
    }
}
