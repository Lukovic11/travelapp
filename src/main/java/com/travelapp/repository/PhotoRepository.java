package com.travelapp.repository;

import com.travelapp.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByImageUrl(String imageUrl);

    List<Photo> findAllByTripId(Long tripId);

    List<Photo> findAllByExperienceId(Long experienceId);
}
