package com.record.trip;

import java.util.Date;

public record CreateTripRecord(String title,
                               String description,
                               Date dateFrom,
                               Date dateTo,
                               String location) {
}
