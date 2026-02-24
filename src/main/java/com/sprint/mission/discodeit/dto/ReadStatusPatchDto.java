package com.sprint.mission.discodeit.dto;

import java.time.Instant;

public record ReadStatusPatchDto(
    Instant newLastReadAt
) {

}
