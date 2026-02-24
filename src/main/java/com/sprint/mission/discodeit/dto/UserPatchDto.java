package com.sprint.mission.discodeit.dto;

public record UserPatchDto(
    String newUsername,
    String newEmail,
    String newPassword
) {

}
