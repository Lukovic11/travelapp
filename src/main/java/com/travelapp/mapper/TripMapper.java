package com.travelapp.mapper;

import com.travelapp.entity.Trip;
import com.travelapp.record.trip.TripListItemRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "destination", source = "destination")
    @Mapping(target = "dateFrom", source = "dateFrom")
    @Mapping(target = "dateTo", source = "dateTo")
    List<TripListItemRecord> toTripListItemDTOList(List<Trip> trips);
}
