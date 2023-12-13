package com.date;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("api/v1/date")
@RequiredArgsConstructor
public class DateController {
    private final DateService dateService;

    @PostMapping("/create-disabled")
    public ResponseEntity<List<TimeCancelledResponse>> createDisabledDate(
            @RequestBody List<String> dateStrings
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        List<DateRequest> request = new ArrayList<>();

        for(String dateString : dateStrings) {
            try {
                //converts date string into date object.
                Date dateObject = simpleDateFormat.parse(dateString);
                //converts date object into DateRequest object and added to list to be handled in the service class.
                DateRequest dateRequest = DateRequest.builder().date(dateObject).build();
                request.add(dateRequest);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(dateService.disableDates(request));
    }

    @GetMapping("/disabled-dates")
    public ResponseEntity<List<DateDisabledResponse>> getDisabledDates() {
        return ResponseEntity.ok(dateService.findAllDisabledDates());
    }

}
