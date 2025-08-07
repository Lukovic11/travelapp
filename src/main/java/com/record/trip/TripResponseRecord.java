package com.record.trip;

import com.record.experience.ExperienceResponseRecord;
import com.record.photo.PhotoResponseRecord;

import java.util.Date;
import java.util.List;

public record TripResponseRecord(Long id,
                                 String title,
                                 String description,
                                 String destination,
                                 Date dateFrom,
                                 Date dateTo,
                                 String location,
                                 List<ExperienceResponseRecord> experiences,
                                 List<PhotoResponseRecord> photos) {
}
