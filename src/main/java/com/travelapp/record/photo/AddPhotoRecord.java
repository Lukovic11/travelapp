package com.travelapp.record.photo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddPhotoRecord(Long tripId,
                             Long experienceId,
                             List<MultipartFile> file) {
}
