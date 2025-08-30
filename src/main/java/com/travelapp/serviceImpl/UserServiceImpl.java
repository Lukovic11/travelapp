package com.travelapp.serviceImpl;

import com.travelapp.entity.User;
import com.travelapp.exception.NotFoundException;
import com.travelapp.mapper.UserMapper;
import com.travelapp.record.user.UserResponseRecord;
import com.travelapp.repository.UserRepository;
import com.travelapp.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseRecord getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
        return mapper.userToUserResponseRecord(user);
    }

    @Override
    public UserResponseRecord getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        return mapper.userToUserResponseRecord(user);
    }
}
