package com.travelapp.record.experience;

import java.time.LocalDate;
import java.util.Optional;

public record CreateExperienceRecord(Optional<String> title,
                                     Optional<String> description,
                                     Optional<LocalDate> date) {
}
