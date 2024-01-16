package com.date;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TimeCancelledResponse> disableDates(List<DateRequest> requests) {

        //this::disableDate applies the disableDate to each request in the list.
        //flat map flattens the steam of lists into a single stream.
        //change
        List<TimeCancelledResponse> responses =  requests.stream()
                .map(this::disableDate)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return responses;
    }

    public List<TimeCancelledResponse> disableDate(DateRequest request) {
        // gets all the times that occur on the date then disables the date, and returns the times
        // to be handled in the front end.

        Optional<DateEntity> optionalDateEntity = dateRepository.findByDate(request.getDate());
        DateEntity dateEntity = null;
        if(optionalDateEntity.isPresent()){
            dateEntity = optionalDateEntity.get();
        }

        if(areTimesCurrentlyPresentOnDateEntity(dateEntity)){
            //if times are present on the selected date entity delete them and return the deleted times.

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
        //search the db for all disabled dates
        List<DateEntity> dateEntities = dateRepository.findByDisabledTrue().stream().toList();
        List<DateDisabledResponse> dateDisabledResponses = dateEntities.stream()
                .map(entity -> DateDisabledResponse
                        .builder()
                        .date(entity.getDate())
                        .build())
                .toList();
        return dateDisabledResponses;
    }

    public boolean areTimesCurrentlyPresentOnDateEntity(DateEntity dateEntity) {

        List<TimeEntity> times = dateEntity.getTimes();

        return !times.isEmpty();

    }
}
