package com.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDataController {
    private final UserService service;

    @PostMapping("/getData")
    public ResponseEntity<Optional<Object[]>> getData(
            @RequestBody String request
    ) {
        return ResponseEntity.ok(service.findUserDataByEmail(request));
    }
}
