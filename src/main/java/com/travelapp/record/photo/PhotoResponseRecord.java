package com.travelapp.record.photo;

import java.util.Optional;

public record PhotoResponseRecord(Long id,
                                  String imageUrl,
                                  Long tripId,
                                  Optional<Long> experienceId) {
}
