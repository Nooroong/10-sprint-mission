package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record MessagePostDto(
    @NotBlank
    String content,

    @NotBlank
    UUID channelId,

    @NotBlank
    UUID authorId
) {

}
