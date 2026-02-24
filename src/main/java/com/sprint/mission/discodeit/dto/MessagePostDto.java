package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record MessagePostDto(
    String content,
    UUID channelId,
    UUID authorId
) {

}
