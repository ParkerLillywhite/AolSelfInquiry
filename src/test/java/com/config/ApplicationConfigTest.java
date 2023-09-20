package com.config;

import com.user.User;
import com.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;
    @BeforeEach
    public void Setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserDetailsService_withValidUsername_returnsUserDetails() {
        String username = "boppsy";
        User user = User.builder()
                .id(1)
                .firstname("borb")
                .lastname("ley")
                .email(username)
                .password("password")
                .build();

        when(userRepository.findByEmail(username)).thenReturn(java.util.Optional.of(user));
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        UserDetails userDetails = applicationConfig.userDetailsService().loadUserByUsername(username);

        assertEquals(userDetails.getUsername(), username);
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    public void testUserDetailsService_givenInvalidUsername_throwsException() {
        String username = "thisguydoesntexist";

        when(userRepository.findByEmail(username)).thenReturn(java.util.Optional.empty());

        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);

        assertThrows(UsernameNotFoundException.class, () -> {
            applicationConfig.userDetailsService().loadUserByUsername(username);
        });
    }
}
