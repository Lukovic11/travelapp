package com.travelapp.service;

import com.travelapp.record.experience.CreateExperienceRecord;
import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.experience.UpdateExperienceRecord;

import java.util.List;

public interface ExperienceService {

    List<ExperienceResponseRecord> findAllByTripId(Long tripId);

    ExperienceResponseRecord findById(Long id);

    ExperienceResponseRecord save(CreateExperienceRecord createExperienceRecord);

    ExperienceResponseRecord update(UpdateExperienceRecord updateExperienceRecord);

    void deleteById(Long id);

}
