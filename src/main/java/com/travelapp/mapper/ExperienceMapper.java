package com.travelapp.mapper;

import com.travelapp.entity.Experience;
import com.travelapp.record.experience.CreateExperienceRecord;
import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.experience.UpdateExperienceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    List<ExperienceResponseRecord> toExperienceResponseRecordList(List<Experience> experiences);

    ExperienceResponseRecord toExperienceResponseRecord(Experience experience);

    Experience toExperience(CreateExperienceRecord createExperienceRecord);

    Experience updateExperienceFromRecord(UpdateExperienceRecord updateExperienceRecord, @MappingTarget Experience experience);

}
