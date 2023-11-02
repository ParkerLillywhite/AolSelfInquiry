package com.date;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DateService {
    private final DateRepository dateRepository;

    public void createDisabledDate(DateRequest dateRequest){
        DateEntity
                .builder()
                .date(dateRequest.getDate())
                .times(new ArrayList<TimeEntity>())
                .disabled(true)
                .build();
    }

    public List<DateDisabledResponse> findAllDisabledDates(){
        List<DateEntity> dateEntities = dateRepository.findByDisabledTrue().stream().toList();
        List<DateDisabledResponse> dateDisabledResponses = dateEntities.stream()
                .map(entity -> DateDisabledResponse
                        .builder()
                        .date(entity.getDate())
                        .disabled(entity.getDisabled())
                        .build())
                .toList();
        return dateDisabledResponses;
    }

    public boolean areTimesCurrentlyPresentOnDateEntity(DateRequest request) {
        Optional<DateEntity> optionalDateEntity = dateRepository.findByDate(request.getDate());

        if(optionalDateEntity.isPresent()) {
            DateEntity dateEntity = optionalDateEntity.get();
            List<TimeEntity> times = dateEntity.getTimes();

            return !times.isEmpty();
        }
        return false;
    }
}
