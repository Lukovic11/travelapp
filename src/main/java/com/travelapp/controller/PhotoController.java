package com.travelapp.controller;

import com.travelapp.record.photo.PhotoResponseRecord;
import com.travelapp.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("trip/{tripId}")
    public ResponseEntity<List<PhotoResponseRecord>> getByTripId(@PathVariable("tripId") Long tripId) {
        return new ResponseEntity<>(photoService.getAllByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("experience/{experienceId}")
    public ResponseEntity<List<PhotoResponseRecord>> getByExperienceId(@PathVariable("experienceId") Long experienceId) {
        return new ResponseEntity<>(photoService.getAllByExperienceId(experienceId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<PhotoResponseRecord>> save(@RequestParam("tripId") Long tripId,
                                                          @RequestParam(value = "experienceId", required = false) Long experienceId,
                                                          @RequestParam("file") List<MultipartFile> files) {
        return new ResponseEntity<>(photoService.save(tripId, experienceId, files), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        photoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
