package com.date;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DateService {
    private final DateRepository dateRepository;
    private final TimeRepository timeRepository;

    public void createDisabledDate(DateRequest dateRequest){
        DateEntity dateEntity = DateEntity
                .builder()
                .date(dateRequest.getDate())
                .times(new ArrayList<TimeEntity>())
                .disabled(true)
                .build();
        dateRepository.save(dateEntity);
    }

    public List<TimeCancelledResponse> disableDate(DateRequest request) {
        Optional<DateEntity> optionalDateEntity = dateRepository.findByDate(request.getDate());
        DateEntity dateEntity = null;
        if(optionalDateEntity.isPresent()){
            dateEntity = optionalDateEntity.get();
        }

        if(areTimesCurrentlyPresentOnDateEntity(dateEntity)){

            List<TimeCancelledResponse> timeCancelledResponses = dateEntity.getTimes()
                    .stream()
                    .map(e -> TimeCancelledResponse
                            .builder()
                            .time(e.getTime())
                            .date(e.getDate().getDate())
                            .user(e.getUser())
                            .build())
                    .toList();

            timeRepository.deleteByEntity(dateEntity);
            createDisabledDate(request);

            return timeCancelledResponses;
        } else {

            timeRepository.deleteByEntity(dateEntity);
            createDisabledDate(request);

            return new ArrayList<>();
        }
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

    public boolean areTimesCurrentlyPresentOnDateEntity(DateEntity dateEntity) {

        List<TimeEntity> times = dateEntity.getTimes();

        return !times.isEmpty();

    }
}
