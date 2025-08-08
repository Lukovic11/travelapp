package com.travelapp.controller;

import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TripListItemRecord>> findAllByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(tripService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseRecord> findById(@PathVariable Long id) {
        return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
    }

}
