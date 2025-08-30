package com.travelapp.service;

import com.travelapp.record.user.UserResponseRecord;

public interface UserService {

    public UserResponseRecord getUserByUsername(String username);

    public UserResponseRecord getUserByEmail(String email);

}
