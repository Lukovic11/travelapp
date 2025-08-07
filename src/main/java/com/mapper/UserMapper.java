package com.mapper;

import com.entity.User;
import com.record.user.UserResponseRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseRecord userToUserResponseRecord(User user);

}
