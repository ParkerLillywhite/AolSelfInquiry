package com.date;

import com.user.Role;
import com.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class DateServiceTest {

    

    @Mock
    private DateRepository dateRepository;

    @Mock
    private TimeRepository timeRepository;

    private DateService dateService;

    private DateEntity supremeDateEntity;
    private List<TimeEntity> timeEntities;
    private User user;
    private TimeEntity timeEntity;
    private DateEntity dateEntityWithTimes;

    private DateRequest dateRequest;
    private DateRequest secondDateRequest;

    private List<DateRequest> dateRequests;


    @BeforeEach
    public void SetUp(){
    //helper
        MockitoAnnotations.initMocks(this);
        dateService = new DateService(dateRepository, timeRepository);

        supremeDateEntity = DateEntity
                .builder()
                .date(new Date(2023, 10,23))
                .times(new ArrayList<>())
                .disabled(true)
                .build();

        timeEntities = new ArrayList<>();

        user = User
                .builder()
                .email("crog@crogland.com")
                .firstname("crog")
                .lastname("bonson")
                .role(Role.USER)
                .build();

        timeEntity = TimeEntity
                .builder()
                .date(supremeDateEntity)
                .time(LocalTime.MIDNIGHT)
                .user(user)
                .build();

        timeEntities.add(timeEntity);

        dateEntityWithTimes = DateEntity
                .builder()
                .times(timeEntities)
                .date(new Date(2023, 10, 23))
                .disabled(true)
                .build();

        dateRequest = DateRequest
                .builder()
                .date(new Date(2023, 10, 23))
                .build();

        secondDateRequest = DateRequest
                .builder()
                .date(new Date(2023, 12, 6))
                .build();


        dateRequests = new ArrayList<>();
        dateRequests.add(dateRequest);
        dateRequests.add(secondDateRequest);

    }

    @Test
    public void testFindAllDisabledDates_withValidEntities_returnsListOfDisabledResponses() {
        List<DateEntity> dateEntities = new ArrayList<>();
        when(dateRepository.findByDisabledTrue()).thenReturn(dateEntities);

        List<DateDisabledResponse> dateDisabledResponses = dateService.findAllDisabledDates();

        assertEquals(dateEntities.size(), dateDisabledResponses.size());
    }

    @Test
    public void testDisabledDate_withPresentTimeEntities_returnsListOfTimeCancelledResponses() {
        when(dateRepository.findByDate(any())).thenReturn(Optional.ofNullable(dateEntityWithTimes));
        doNothing().when(timeRepository).deleteByEntity(any());


        List<TimeCancelledResponse> timeCancelledResponses = dateService.disableDate(dateRequest);

        assertEquals(timeCancelledResponses.get(0).getDate(), dateEntityWithTimes.getDate());

    }

    @Test
    public void testDisableDate_withNoTimeEntities_returnsEmptyList() {
        when(dateRepository.findByDate(any())).thenReturn(Optional.ofNullable(supremeDateEntity));
        doNothing().when(timeRepository).deleteByEntity(any());

        List<TimeCancelledResponse> timeCancelledResponses = dateService.disableDate(dateRequest);

        assertTrue(timeCancelledResponses.isEmpty());
    }

    @Test
    public void testDisableDates_withMultipleRequests_returnsEmptyList() {
        when(dateRepository.findByDate(any())).thenReturn(Optional.of(supremeDateEntity));
        doNothing().when(timeRepository).deleteByEntity(any());

        List<TimeCancelledResponse> timeCancelledResponses = dateService.disableDates(dateRequests);

        assertTrue(timeCancelledResponses.isEmpty());
    }

    @Test
    public void testDisableDates_withValidTimesPresentOnEntity_returnsTimeCancelledResponses() {
        when(dateRepository.findByDate(any())).thenReturn(Optional.of(dateEntityWithTimes));
        doNothing().when(timeRepository).deleteByEntity(any());

        List<TimeCancelledResponse> response = dateService.disableDates(dateRequests);

        assertEquals(response.get(0).getDate(), dateRequest.getDate());
    }

}
