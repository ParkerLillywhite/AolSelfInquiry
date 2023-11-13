package com.date;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
@RequestMapping("api/v1/date")
@RequiredArgsConstructor
public class DateController {
    private final DateService dateService;

    @PostMapping("/create-disabled")
    public ResponseEntity<List<TimeCancelledResponse>> createDisabledDate(
            @RequestBody DateRequest request
    ) {
        return ResponseEntity.ok(dateService.disableDate(request));
    }

    @GetMapping("/disabled-dates")
    public ResponseEntity<List<DateDisabledResponse>> getDisabledDates() {
        return ResponseEntity.ok(dateService.findAllDisabledDates());
    }

}
