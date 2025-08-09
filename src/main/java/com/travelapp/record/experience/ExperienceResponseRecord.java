package com.travelapp.record.experience;

import com.travelapp.record.photo.PhotoResponseRecord;

import java.time.LocalDate;
import java.util.List;

public record ExperienceResponseRecord(Long id,
                                       String title,
                                       String description,
                                       LocalDate date,
                                       List<PhotoResponseRecord> photos) {
}
