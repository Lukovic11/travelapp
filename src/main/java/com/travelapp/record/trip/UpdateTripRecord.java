package com.travelapp.record.trip;

import java.time.LocalDate;

public record UpdateTripRecord(Long id,
                               String title,
                               String description,
                               LocalDate dateFrom,
                               LocalDate dateTo,
                               String location) {
}
