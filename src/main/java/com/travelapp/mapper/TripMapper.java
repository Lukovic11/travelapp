package com.travelapp.mapper;

import com.travelapp.entity.Trip;
import com.travelapp.record.trip.CreateTripRecord;
import com.travelapp.record.trip.TripListItemRecord;
import com.travelapp.record.trip.TripResponseRecord;
import com.travelapp.record.trip.UpdateTripRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    List<TripListItemRecord> toTripListItemRecordList(List<Trip> trips);

    TripResponseRecord toTripResponseRecord(Trip trip);

    Trip toTrip(CreateTripRecord createTripRecord);

    Trip toTrip(UpdateTripRecord updateTripRecord);

    void updateTripFromRecord(UpdateTripRecord record, @MappingTarget Trip trip);

}
