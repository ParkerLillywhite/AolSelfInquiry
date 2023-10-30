package com.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDataController {
    private final UserService service;

    @GetMapping("/get-data/{email}")
    public ResponseEntity<UserFilteredResponse> getData(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(service.findUserDataByEmail(email));
    }
}
