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
import com.travelapp.service.PhotoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final PhotoService photoService;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, UserRepository userRepository,
                                 TripRepository tripRepository, ExperienceMapper experienceMapper, PhotoService photoService) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.experienceMapper = experienceMapper;
        this.photoService = photoService;
    }

    @Override
    public List<ExperienceResponseRecord> findAllByTripId(Long tripId) {
        User currentUser = getCurrentUser();
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
        User currentUser = getCurrentUser();
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
        User currentUser = getCurrentUser();
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
        User currentUser = getCurrentUser();
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
        User currentUser = getCurrentUser();
        Experience experience = experienceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ExperienceService delete() :: Experience not found with id "
                        + id));
        if (!experience.getTrip().getUser().equals(currentUser)) {
            throw new ForbiddenException("ExperienceService delete() :: User " + currentUser.getUsername()
                    + " does not have access to this experience");
        }
        photoService.deleteByExperienceId(id);
        experienceRepository.deleteById(id);
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("ExperienceService getCurrentUser() :: User not found with username "
                        + userDetails.getUsername()));
    }
}
