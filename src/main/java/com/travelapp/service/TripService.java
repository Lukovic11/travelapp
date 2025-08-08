package com.travelapp.service;

import com.travelapp.record.trip.TripListItemRecord;

import java.util.List;

public interface TripService {

    List<TripListItemRecord> findAllByUserId(Long userId);

}
