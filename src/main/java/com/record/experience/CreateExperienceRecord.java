package com.record.experience;

import java.util.Date;
import java.util.Optional;

public record CreateExperienceRecord(Optional<String> title,
                                     Optional<String> description,
                                     Optional<Date> date) {
}
