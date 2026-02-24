package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public record ReadStatusPatchDto(
    @NotBlank
    Instant newLastReadAt
) {

}
