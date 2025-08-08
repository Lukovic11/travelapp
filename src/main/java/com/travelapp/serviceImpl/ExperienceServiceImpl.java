package com.travelapp.serviceImpl;

import com.travelapp.mapper.ExperienceMapper;
import com.travelapp.record.experience.CreateExperienceRecord;
import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.experience.UpdateExperienceRecord;
import com.travelapp.repository.ExperienceRepository;
import com.travelapp.service.ExperienceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public List<ExperienceResponseRecord> findAllByTripId(Long tripId) {
        return experienceMapper.toExperienceResponseRecordList(experienceRepository.findAllByTripId(tripId));
    }

    @Override
    public ExperienceResponseRecord findById(Long id) {
        return null;
    }

    @Override
    public ExperienceResponseRecord save(CreateExperienceRecord createExperienceRecord) {
        return null;
    }

    @Override
    public ExperienceResponseRecord update(UpdateExperienceRecord updateExperienceRecord) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
