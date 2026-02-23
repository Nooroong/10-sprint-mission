package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User 생성 정보")
public record UserPostDto(
    String nickName,
    String userName,
    String email,
    String phoneNumber,
    String password
) {

}
