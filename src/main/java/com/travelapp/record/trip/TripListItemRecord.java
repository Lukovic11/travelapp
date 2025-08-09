package com.travelapp.record.trip;

import java.time.LocalDate;

public record TripListItemRecord(Long id,
                                 String title,
                                 String location,
                                 LocalDate dateFrom,
                                 LocalDate dateTo) {
}
