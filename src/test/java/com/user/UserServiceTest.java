package com.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.user.Role.USER;
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
        String email = "nogg";

        Role role = USER;

        User response = User
                .builder()
                .firstname("nog")
                .lastname("noggus")
                .email("nogg")
                .password("nog")
                .role(role)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(response));

        UserFilteredResponse userFilteredResponse = userService.findUserDataByEmail(email);

        assertEquals("nog", userFilteredResponse.getFirstname());
        assertEquals("noggus", userFilteredResponse.getLastname());
        assertEquals("nogg", userFilteredResponse.getEmail());
        assertEquals("USER", String.valueOf(userFilteredResponse.getRole()));
    }

    @Test
    public void testFindUserDataByEmail_withInvalidEmail_returnsNull() {
        String email = "bonkus";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserFilteredResponse userFilteredResponse = userService.findUserDataByEmail(email);

        assertNull(userFilteredResponse);
    }

}
