package com.travelapp.mapper;

import com.travelapp.entity.Photo;
import com.travelapp.record.photo.PhotoResponseRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "experienceId", source = "experience.id")
    PhotoResponseRecord toPhotoResponseRecord(Photo photo);

    List<PhotoResponseRecord> toPhotoResponseRecords(List<Photo> photos);
}
