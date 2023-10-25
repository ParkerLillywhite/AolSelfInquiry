package com.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserFilteredResponse findUserDataByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User userData = optionalUser.get();

            UserFilteredResponse userFilteredResponse = UserFilteredResponse
                    .builder()
                    .firstname(userData.getFirstname())
                    .lastname(userData.getLastname())
                    .email(userData.getEmail())
                    .role(userData.getRole())
                    .build();

            return userFilteredResponse;
        }
        return null;

    }



}
