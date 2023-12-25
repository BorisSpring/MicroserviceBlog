package com.main.authservice.mappers;

import com.main.authservice.domain.User;
import com.main.authservice.model.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import java.io.IOException;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

     UserDto userToUserDto(User user) throws IOException;
}
