package com.travelapp.record.photo;

public record PhotoResponseRecord(Long id,
                                  String imageUrl,
                                  Long tripId,
                                  Long experienceId) {
}
