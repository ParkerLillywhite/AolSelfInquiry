package com.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindUserDataByEmail_givenValidInfo_returnsUserObject() {
        String email = "validEmail@validity.com";

        Object[] response = {
                "Bognor",
                "Cheepus",
                "validEmail@validity.com",
                "USER"
        };

        when(userRepository.findUserDataByEmail(anyString())).thenReturn(Optional.of(response));

        UserFilteredResponse userFilteredResponse = userService.findUserDataByEmail(email);

        assertEquals("Bognor", userFilteredResponse.getFirstname());
        assertEquals("Cheepus", userFilteredResponse.getLastname());
        assertEquals("validEmail@validity.com", userFilteredResponse.getEmail());
        assertEquals("USER", String.valueOf(userFilteredResponse.getRole()));
    }

    @Test
    public void testFindUserDataByEmail_withInvalidEmail_returnsNull() {
        String email = "bonkus";

        Optional<Object[]> resultOptional = Optional.empty();

        when(userRepository.findUserDataByEmail(email)).thenReturn(resultOptional);

        UserFilteredResponse userFilteredResponse = userService.findUserDataByEmail(email);

        assertNull(userFilteredResponse);
    }

}
