package com.travelapp.controller;

import com.travelapp.record.experience.ExperienceResponseRecord;
import com.travelapp.service.ExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
