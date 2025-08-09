package com.travelapp.serviceImpl;

import com.travelapp.entity.Experience;
import com.travelapp.entity.Photo;
import com.travelapp.entity.Trip;
import com.travelapp.entity.User;
import com.travelapp.exception.BadRequestException;
import com.travelapp.exception.ForbiddenException;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.PhotoMapper;
import com.travelapp.record.photo.PhotoResponseRecord;
import com.travelapp.repository.ExperienceRepository;
import com.travelapp.repository.PhotoRepository;
import com.travelapp.repository.TripRepository;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final TripRepository tripRepository;

    private final ExperienceRepository experienceRepository;

    private final UserRepository userRepository;

    private final PhotoMapper photoMapper;

    @Value("${app.photo.directory}")
    private String PHOTO_DIR;

    public PhotoServiceImpl(PhotoRepository photoRepository, TripRepository tripRepository, ExperienceRepository experienceRepository, UserRepository userRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.tripRepository = tripRepository;
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
        this.photoMapper = photoMapper;
    }

    @Override
    public List<PhotoResponseRecord> getAllByTripId(Long tripId) {
        // check if that tripId exists
        return photoMapper.toPhotoResponseRecords(photoRepository.findAllByTripId(tripId));
    }

    @Override
    public List<PhotoResponseRecord> getAllByExperienceId(Long experienceId) {
        // check if that experienceId exists
        return photoMapper.toPhotoResponseRecords(photoRepository.findAllByExperienceId(experienceId));
    }

    @Override
    public List<PhotoResponseRecord> saveMultiple(Long tripId, Long experienceId, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new BadRequestException("PhotoService saveMultiple() :: No photos provided");
        }

        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");

        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException("PhotoService saveMultiple() :: Trip not found with id "
                        + tripId));

        if (!trip.getUser().equals(currentUser)) {
            throw new ForbiddenException("PhotoService saveMultiple() :: User " + currentUser.getUsername()
                    + " does not have access to this trip");
        }

        Experience experience = null;
        if (experienceId != null) {
            experience = experienceRepository.findById(experienceId).orElseThrow(
                    () -> new NotFoundException("PhotoService saveMultiple() :: Experience not found with id "
                            + experienceId));
            if (!experience.getTrip().getUser().equals(currentUser)) {
                throw new ForbiddenException("PhotoService saveMultiple() :: User " + currentUser.getUsername()
                        + " does not have access to this experience");
            }
        }

        File directory = new File(PHOTO_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("PhotoService saveMultiple() :: Failed to create photo directory");
            }
        }

        List<Photo> savedPhotos = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) {
                    continue;
                }

                String fileName = file.getOriginalFilename();
                if (fileName == null || fileName.trim().isEmpty()) {
                    throw new BadRequestException("PhotoService saveMultiple() :: File must have a name");
                }

                if (!isValidImageExtension(fileName)) {
                    throw new BadRequestException("PhotoService saveMultiple() :: Invalid file type: " + fileName);
                }

                String uniqueFileName = generateUniqueFileName(fileName);

                Path filePath = Paths.get(PHOTO_DIR + File.separator + uniqueFileName);
                Files.write(filePath, file.getBytes());

                Photo photo = new Photo();
                photo.setImageUrl(uniqueFileName);
                photo.setTrip(trip);
                if (experience != null) {
                    photo.setExperience(experience);
                }

                savedPhotos.add(photoRepository.save(photo));

            }
        } catch (IOException e) {
            cleanupSavedFiles(savedPhotos);
            throw new RuntimeException("PhotoService saveMultiple() :: Error saving files: " + e.getMessage(), e);
        }
        return savedPhotos.stream()
                .map(photoMapper::toPhotoResponseRecord)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMultiple(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException("PhotoService deleteMultiple() :: No photo IDs provided");
        }

        // later on get user from context
        User currentUser = userRepository.findByUsername("ivana");

        List<Photo> photosToDelete = new ArrayList<>();

        for (Long id : ids) {
            if (id == null) {
                continue;
            }

            Photo photo = photoRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("PhotoService deleteMultiple() :: Photo not found with ID: " + id));

            if (!photo.getTrip().getUser().equals(currentUser)) {
                throw new ForbiddenException("PhotoService deleteMultiple() :: User " + currentUser.getUsername()
                        + " does not have access to photo with ID: " + id);
            }

            photosToDelete.add(photo);
        }

        List<Photo> failedDeletions = new ArrayList<>();

        for (Photo photo : photosToDelete) {
            try {
                Path photoPath = Paths.get(PHOTO_DIR + File.separator + photo.getImageUrl());
                boolean fileDeleted = Files.deleteIfExists(photoPath);

                if (!fileDeleted) {
                    System.err.println("PhotoService deleteMultiple() :: Warning: File not found on disk: " + photo.getImageUrl());
                }

                photoRepository.delete(photo);

            } catch (IOException e) {
                System.err.println("PhotoService deleteMultiple() :: Failed to delete file: " + photo.getImageUrl() + " - " + e.getMessage());
                failedDeletions.add(photo);
            } catch (Exception e) {
                System.err.println("PhotoService deleteMultiple() :: Failed to delete photo from database: " + photo.getImageUrl() + " - " + e.getMessage());
                failedDeletions.add(photo);
            }
        }

        if (!failedDeletions.isEmpty()) {
            String failedIds = failedDeletions.stream()
                    .map(photo -> String.valueOf(photo.getId()))
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("PhotoService deleteMultiple() :: Failed to delete photos with IDs: " + failedIds);
        }
    }

    private boolean isValidImageExtension(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return Set.of("jpg", "jpeg", "png", "gif", "webp").contains(extension);
    }

    private String generateUniqueFileName(String fileName) {
        String extension = getFileExtension(fileName);
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);

        return baseName + "_" + timestamp + "_" + randomSuffix + "." + extension;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1) : "";
    }


    private void cleanupSavedFiles(List<Photo> savedPhotos) {
        for (Photo photo : savedPhotos) {
            try {
                Path photoPath = Paths.get(PHOTO_DIR + File.separator + photo.getImageUrl());
                Files.deleteIfExists(photoPath);
                photoRepository.delete(photo);
            } catch (IOException e) {
                System.err.println("Failed to cleanup file: " + photo.getImageUrl());
            }
        }
    }
}
