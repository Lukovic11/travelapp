package com.travelapp.serviceImpl;

import com.travelapp.entity.Trip;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.TripMapper;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
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
        return tripMapper.toTripListItemRecordList(
                tripRepository.findAllByUserId(userId)
        );
    }

    @Override
    public TripResponseRecord findById(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new NotFoundException("Trip not found with id " + id));
        return tripMapper.toTripResponseRecord(trip);
    }
}
