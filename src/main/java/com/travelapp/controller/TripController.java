package com.travelapp.controller;

import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

        @GetMapping("/{userId}")
    public List<TripListItemRecord> findAllByUserId(@PathVariable Long userId) {
        return tripService.findAllByUserId(userId);
    }

}
