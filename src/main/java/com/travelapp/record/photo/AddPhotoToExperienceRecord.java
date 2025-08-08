package com.travelapp.record.photo;

import org.springframework.web.multipart.MultipartFile;

public record AddPhotoToExperienceRecord(Long experienceId,
                                         MultipartFile photo) {
}
