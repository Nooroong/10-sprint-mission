package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UserStatusPostDto(
    @NotBlank
    UUID userId
) {

}
