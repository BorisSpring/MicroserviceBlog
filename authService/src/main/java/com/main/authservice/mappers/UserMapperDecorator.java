package com.main.authservice.mappers;

import com.main.authservice.domain.User;
import com.main.authservice.model.UserDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public abstract class UserMapperDecorator implements  UserMapper{

    @Autowired
    private   UserMapper userMapper;

    @Override
    public UserDto userToUserDto(User user) throws IOException {
        UserDto userDto = userMapper.userToUserDto(user);
        var imageFile = new ClassPathResource("static/" + user.getImageName());
        if(imageFile.exists()){
            userDto.setImage(StreamUtils.copyToByteArray(imageFile.getInputStream()));
        }
        return  userDto;
    }

}
