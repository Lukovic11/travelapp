package com.record.experience;

import com.record.photo.PhotoResponseRecord;

import java.util.Date;
import java.util.List;

public record ExperienceResponseRecord(Long id,
                                       String title,
                                       String description,
                                       Date date,
                                       List<PhotoResponseRecord> photos) {
}
