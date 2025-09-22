package com.travelapp.service;

import com.travelapp.record.photo.PhotoResponseRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    List<PhotoResponseRecord> getAllByTripId(Long tripId);

    List<PhotoResponseRecord> getAllByExperienceId(Long experienceId);

    List<PhotoResponseRecord> save(Long tripId, Long experienceId, List<MultipartFile> files);

    void delete(Long id);

    void deleteByTripId(Long tripId);

    void deleteByExperienceId(Long experienceId);
}
