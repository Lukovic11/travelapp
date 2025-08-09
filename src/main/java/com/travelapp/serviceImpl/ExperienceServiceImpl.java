package com.travelapp.serviceImpl;

import com.travelapp.entity.Experience;
import com.travelapp.entity.Trip;
import com.travelapp.entity.User;
import com.travelapp.exception.ForbiddenException;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.ExperienceMapper;
import com.travelapp.record.experience.CreateExperienceRecord;
import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.experience.UpdateExperienceRecord;
import com.travelapp.repository.ExperienceRepository;
import com.travelapp.repository.TripRepository;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.ExperienceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    private final UserRepository userRepository;

    private final TripRepository tripRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, UserRepository userRepository,
                                 TripRepository tripRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public List<ExperienceResponseRecord> findAllByTripId(Long tripId) {
        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException("ExperienceService findAllByTripId() :: Trip not found with id " + tripId));
        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService findAllByTripId() :: User " + currentUser.getUsername() +
                    " does not have access to this trip");
        }
        return experienceMapper.toExperienceResponseRecordList(experienceRepository.findAllByTripId(tripId));
    }

    @Override
    public ExperienceResponseRecord findById(Long id) {
        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");
        Experience experience = experienceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ExperienceService findById() :: Experience not found with id " + id));
        if (!experience.getTrip().getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService findById() :: User " + currentUser.getUsername() +
                    " does not have access to this experience");
        }
        return experienceMapper.toExperienceResponseRecord(experience);
    }

    @Override
    public ExperienceResponseRecord save(CreateExperienceRecord createExperienceRecord) {
        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");
        Trip trip = tripRepository.findById(createExperienceRecord.tripId()).orElseThrow(
                () -> new NotFoundException("ExperienceService save() :: Trip not found with id "
                        + createExperienceRecord.tripId()));
        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService save() :: User " + currentUser.getUsername() +
                    " does not have access to this trip");
        }
        Experience experience = experienceMapper.toExperience(createExperienceRecord);
        experience.setTrip(trip);
        return experienceMapper.toExperienceResponseRecord(experienceRepository.save(experience));
    }

    @Override
    public ExperienceResponseRecord update(UpdateExperienceRecord updateExperienceRecord) {
        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");
        Experience experience = experienceRepository.findById(updateExperienceRecord.id()).orElseThrow(
                () -> new NotFoundException("ExperienceService update() :: Experience not found with id "
                        + updateExperienceRecord.id()));
        if (!experience.getTrip().getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService update() :: User " + currentUser.getUsername()
                    + " does not have access to this experience");
        }
        return experienceMapper.toExperienceResponseRecord(experienceRepository
                .save(experienceMapper.updateExperienceFromRecord(updateExperienceRecord, experience)));
    }

    @Override
    public void deleteById(Long id) {
        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");
        Experience experience = experienceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ExperienceService delete() :: Experience not found with id "
                        + id));
        if (!experience.getTrip().getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService delete() :: User " + currentUser.getUsername()
                    + " does not have access to this experience");
        }
        experienceRepository.deleteById(id);
    }
}
