package com.travelapp.record.trip;

import java.time.LocalDate;
import java.util.Optional;

public record UpdateTripRecord(Optional<String> title,
                               Optional<String> description,
                               Optional<LocalDate> dateFrom,
                               Optional<LocalDate> dateTo,
                               Optional<String> location) {
}
