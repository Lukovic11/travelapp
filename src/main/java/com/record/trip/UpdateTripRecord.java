package com.record.trip;

import java.util.Date;
import java.util.Optional;

public record UpdateTripRecord(Optional<String> title,
                               Optional<String> description,
                               Optional<Date> dateFrom,
                               Optional<Date> dateTo,
                               Optional<String> location) {
}
