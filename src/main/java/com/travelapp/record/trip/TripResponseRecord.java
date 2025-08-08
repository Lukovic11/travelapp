package com.travelapp.record.trip;

import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.photo.PhotoResponseRecord;

import java.time.LocalDate;
import java.util.List;

public record TripResponseRecord(Long id,
                                 String title,
                                 String description,
                                 String destination,
                                 LocalDate dateFrom,
                                 LocalDate dateTo,
                                 String location,
                                 List<ExperienceResponseRecord> experiences,
                                 List<PhotoResponseRecord> photos) {
}
