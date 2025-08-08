package com.travelapp.service;

import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;

import java.util.List;

public interface TripService {

    List<TripListItemRecord> findAllByUserId(Long userId);

    TripResponseRecord findById(Long id);

    TripResponseRecord save(CreateTripRecord createTripRecord);

}
