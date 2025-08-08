package com.travelapp.record.experience;

import java.time.LocalDate;

public record CreateExperienceRecord(String title,
                                     String description,
                                     LocalDate date) {
}
