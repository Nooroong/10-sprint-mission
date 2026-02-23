package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.util.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserStatus", description = "UserStatus controller 입니다.")
public class UserStatusController {

  private final UserStatusService userStatusService;

  // 사용자 온라인 상태 업데이트
  @RequestMapping(value = "/api/users/{userId}/userStatus", method = RequestMethod.PATCH)
  @Operation(summary = "User 온라인 상태 업데이트", operationId = "updateUserStatusByUserId")
  public ResponseEntity<SuccessResponse<UserStatusResponseDto>> updateUserStatus(
      @Parameter(name = "userId", description = "상태를 변경할 User ID") @PathVariable UUID userId) {
    return ResponseEntity.ok(SuccessResponse.success(userStatusService.updateByUserId(userId)));
  }
}
