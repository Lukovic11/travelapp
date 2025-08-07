package com.record.photo;

import org.springframework.web.multipart.MultipartFile;

public record AddPhotoToTripRecord(Long tripId,
                                   MultipartFile photo) {
}
