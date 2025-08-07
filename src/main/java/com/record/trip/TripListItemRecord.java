package com.record.trip;

import java.util.Date;

public record TripListItemRecord(Long id,
                                 String title,
                                 String destination,
                                 Date dateFrom,
                                 Date dateTo) {
}
