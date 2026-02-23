package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserStatusMapper {

  public UserStatusResponseDto toResponseDto(UserStatus userStatus) {
    return new UserStatusResponseDto(
        userStatus.getUserId(),
        userStatus.getLastActiveAt()
    );
  }
}
