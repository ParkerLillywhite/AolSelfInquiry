package com.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserFilteredResponse findUserDataByEmail(String email) {
        Optional<Object[]> resultOptional = userRepository.findUserDataByEmail(email);

        if(resultOptional.isPresent()) {

            Object[] result = resultOptional.get();

            UserFilteredResponse userFilteredResponse = UserFilteredResponse
                    .builder()
                    .firstname(String.valueOf(result[0]))
                    .lastname(String.valueOf(result[1]))
                    .email(String.valueOf(result[2]))
                    .role(Role.valueOf(String.valueOf(result[3])))
                    .build();
            return userFilteredResponse;
        }
        return null;
    }

}
