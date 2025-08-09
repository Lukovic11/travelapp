package com.travelapp.mapper;

import com.travelapp.entity.User;
import com.travelapp.record.user.UserResponseRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseRecord userToUserResponseRecord(User user);

}
