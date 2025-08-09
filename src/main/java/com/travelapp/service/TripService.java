package com.travelapp.service;

import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.record.trip.UpdateTripRecord;

import java.util.List;

public interface TripService {

    List<TripListItemRecord> findAllByUserId(Long userId);

    TripResponseRecord findById(Long id);

    TripResponseRecord save(CreateTripRecord createTripRecord);

    TripResponseRecord update(UpdateTripRecord updateTripRecord);

    void deleteById(Long id);

}
