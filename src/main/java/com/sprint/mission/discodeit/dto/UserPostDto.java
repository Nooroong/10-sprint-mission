package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User 생성 정보")
public record UserPostDto(
    String nickName,
    @NotBlank
    String username,
    String email,
    String phoneNumber,
    String password
) {

}
