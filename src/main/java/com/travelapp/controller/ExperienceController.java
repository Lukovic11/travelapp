package com.travelapp.controller;

import com.travelapp.record.experience.CreateExperienceRecord;
import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.record.experience.UpdateExperienceRecord;
import com.travelapp.service.ExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<ExperienceResponseRecord>> findAllByTripId(@PathVariable("tripId") Long tripId) {
        return new ResponseEntity<>(experienceService.findAllByTripId(tripId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponseRecord> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(experienceService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExperienceResponseRecord> save(@RequestBody CreateExperienceRecord createExperienceRecord) {
        return new ResponseEntity<>(experienceService.save(createExperienceRecord), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ExperienceResponseRecord> update(@RequestBody UpdateExperienceRecord updateExperienceRecord) {
        return new ResponseEntity<>(experienceService.update(updateExperienceRecord), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        experienceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
