package com.travelapp.record.experience;

import java.time.LocalDate;

public record UpdateExperienceRecord(Long id,
                                     String title,
                                     String description,
                                     LocalDate date) {
}
