package com.date;

import com.user.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class DateServiceTest {

    @Mock
    private DateRepository dateRepository;

    private DateService dateService;

    @BeforeEach
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
        dateService = new DateService(dateRepository);
    }

    @Test
    public void testFindAllDisbaledDates_withValidEntities_returnsListOfDisabledResponses() {
        List<DateEntity> dateEntities = new ArrayList<>();
        when(dateRepository.findByDisabledTrue()).thenReturn(dateEntities);

        List<DateDisabledResponse> dateDisabledResponses = dateService.findAllDisabledDates();

        assertEquals(dateEntities.size(), dateDisabledResponses.size());
        assertEquals(dateDisabledResponses.get(1).getDisabled(), true);
    }

    @Test
    public void testAreTimesCurrentlyPresentOnDateEntity_withEntityWithoutTimes_returnsFalse() {
        DateEntity dateEntity = DateEntity
                .builder()
                .date(new Date())
                .times(new ArrayList<TimeEntity>())
                .disabled(true)
                .build();

        DateRequest dateRequest = DateRequest
                .builder()
                .date(new Date())
                .build();

        when(dateRepository.findByDate(any())).thenReturn(Optional.ofNullable(dateEntity));

        boolean result = dateService.areTimesCurrentlyPresentOnDateEntity(dateRequest);

        assertEquals(result, false);
    }

    @Test
    public void testAreTimesCurrentlyPresentOnDateEntity_withValidTimes_returnsTrue() {

        List<TimeEntity> times = new ArrayList<>();

        DateRequest dateRequest = DateRequest
                .builder()
                .date(new Date())
                .build();

        DateEntity firstDateEntity = DateEntity
                .builder()
                .date(new Date())
                .times(new ArrayList<>())
                .disabled(true)
                .build();

        TimeEntity timeEntity = TimeEntity
                .builder()
                .date(firstDateEntity)
                .time(LocalTime.of(15, 30))
                .user(new User())
                .build();

        times.add(timeEntity);

        DateEntity dateEntity = DateEntity
                .builder()
                .date(new Date())
                .times(times)
                .disabled(true)
                .build();

        when(dateRepository.findByDate(any())).thenReturn(Optional.ofNullable(dateEntity));

        boolean result = dateService.areTimesCurrentlyPresentOnDateEntity(dateRequest);

        assertEquals(result, true);
    }
}
