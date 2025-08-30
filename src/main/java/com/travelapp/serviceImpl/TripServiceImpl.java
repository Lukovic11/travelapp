package com.travelapp.serviceImpl;

import com.travelapp.entity.Trip;
import com.travelapp.entity.User;
import com.travelapp.exception.ForbiddenException;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.TripMapper;
import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.record.trip.UpdateTripRecord;
import com.travelapp.repository.TripRepository;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.PhotoService;
import com.travelapp.service.TripService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    private final UserRepository userRepository;

    private final TripMapper tripMapper;

    private final PhotoService photoService;

    public TripServiceImpl(TripRepository tripRepository, UserRepository userRepository, TripMapper tripMapper, PhotoService photoService) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.tripMapper = tripMapper;
        this.photoService = photoService;
    }

    @Override
    public List<TripListItemRecord> findAll() {
        User currentUser = getCurrentUser();
        return tripMapper.toTripListItemRecordList(
                tripRepository.findAllByUserId(currentUser.getId())
        );
    }

    @Override
    public TripResponseRecord findById(Long id) {
        User currentUser = getCurrentUser();
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new NotFoundException("TripService findById() :: Trip not found with id " + id));
        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("TripService findById() :: User " + currentUser.getUsername()
                    + " does not have access to this trip");
        }
        return tripMapper.toTripResponseRecord(trip);
    }

    @Override
    public TripResponseRecord save(CreateTripRecord createTripRecord) {
        User currentUser = getCurrentUser();
        Trip trip = tripMapper.toTrip(createTripRecord);
        trip.setUser(currentUser);
        return tripMapper.toTripResponseRecord(tripRepository.save(trip));
    }

    @Override
    public TripResponseRecord update(UpdateTripRecord updateTripRecord) {
        User currentUser = getCurrentUser();
        Trip trip = tripRepository.findById(updateTripRecord.id()).orElseThrow(
                () -> new NotFoundException("TripService update() :: " +
                        "Trip cannot be updated for there is no trip found with id " + updateTripRecord.id()));
        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("TripService update() :: User " + currentUser.getUsername()
                    + " does not have access to this trip");
        }
        tripMapper.updateTripFromRecord(updateTripRecord, trip);
        return tripMapper.toTripResponseRecord(tripRepository.save(trip));
    }

    @Override
    public void deleteById(Long id) {
        User currentUser = getCurrentUser();
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new NotFoundException("TripService delete() :: " +
                        "Trip cannot be deleted for there is no trip found with id " + id));
        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("TripService delete() :: User " + currentUser.getUsername()
                    + " does not have access to this trip");
        }
        photoService.deleteByTripId(id);
        tripRepository.deleteById(id);
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("TripService getCurrentUser() :: User not found with username "
                        + userDetails.getUsername()));
    }
}
