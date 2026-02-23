package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.UserPostDto;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toUser(UserPostDto userPostDTO) {
    return new User(
        userPostDTO.nickName(),
        userPostDTO.userName(),
        userPostDTO.email(),
        userPostDTO.phoneNumber(),
        userPostDTO.password()
    );
  }

  public UserDto toUserResponseDto(User user, boolean online) {
    return new UserDto(
        user.getId(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        online
    );
  }
}
