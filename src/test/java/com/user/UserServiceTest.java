package com.user;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
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

        when(userRepository.findUserDataByEmail(anyString())).thenReturn(response);
    }

}
