package com.travelapp.controller;

import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.record.trip.UpdateTripRecord;
import com.travelapp.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<TripResponseRecord> save(@RequestBody CreateTripRecord createTripRecord) {
        return new ResponseEntity<>(tripService.save(createTripRecord), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TripResponseRecord> update(@RequestBody UpdateTripRecord updateTripRecord) {
        return new ResponseEntity<>(tripService.update(updateTripRecord), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
