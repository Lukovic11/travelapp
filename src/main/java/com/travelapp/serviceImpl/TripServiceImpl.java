package com.travelapp.serviceImpl;

import com.travelapp.entity.Trip;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.TripMapper;
import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.record.trip.UpdateTripRecord;
import com.travelapp.repository.TripRepository;
import com.travelapp.repository.UserRepository;
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

    private final UserRepository userRepository;

    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, UserRepository userRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
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
        // later on add check if current user should be able to access this object
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new NotFoundException("TripService findById() :: Trip not found with id " + id));
        return tripMapper.toTripResponseRecord(trip);
    }

    @Override
    public TripResponseRecord save(CreateTripRecord createTripRecord) {
        Trip trip = tripMapper.toTrip(createTripRecord);
        trip.setUser(userRepository.findById(createTripRecord.userId()).orElseThrow(() -> new BadRequestException("TripService save() :: Trip cannot be saved for there is no user with id " + createTripRecord.userId())));
        return tripMapper.toTripResponseRecord(tripRepository.save(trip));
    }

    @Override
    public TripResponseRecord update(UpdateTripRecord updateTripRecord) {
        // later on add check if current user should be able to update this object
        Trip trip = tripRepository.findById(updateTripRecord.id()).orElseThrow(() -> new NotFoundException("TripService update() :: Trip cannot be updated for there is no trip found with id " + updateTripRecord.id()));
        tripMapper.updateTripFromRecord(updateTripRecord, trip);
        return tripMapper.toTripResponseRecord(tripRepository.save(trip));
    }

    @Override
    public void deleteById(Long id) {
        // later on add check if current user should be able to delete this object
        tripRepository.deleteById(id);
    }
}
