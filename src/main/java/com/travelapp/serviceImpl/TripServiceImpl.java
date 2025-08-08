package com.travelapp.serviceImpl;

import com.travelapp.mapper.TripMapper;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.repository.TripRepository;
import com.travelapp.service.TripService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public List<TripListItemRecord> findAllByUserId(Long userId) {
        return tripMapper.toTripListItemDTOList(
                tripRepository.findAllByUserId(userId)
        );
    }
}
