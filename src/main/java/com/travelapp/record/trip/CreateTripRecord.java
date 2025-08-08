package com.travelapp.record.trip;


import java.time.LocalDate;

public record CreateTripRecord(String title,
                               String description,
                               LocalDate dateFrom,
                               LocalDate dateTo,
                               String location,
                               Long userId) {
}
