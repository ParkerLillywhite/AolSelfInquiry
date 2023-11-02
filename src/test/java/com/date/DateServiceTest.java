package com.date;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
        when(dateRepository.findByDate(any()).thenReturn(Optional.ofNullable(dateEntity));
    }
}
