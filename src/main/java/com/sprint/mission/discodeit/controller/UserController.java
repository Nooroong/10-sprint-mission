package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.UserPatchDto;
import com.sprint.mission.discodeit.dto.UserPostDto;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.util.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User controller 입니다.")
public class UserController {

  private final UserService userService;

  @RequestMapping(method = RequestMethod.GET)
  @Operation(summary = "전체 User 목록 조회", operationId = "findAll")
  public ResponseEntity<SuccessResponse<List<UserDto>>> findAll() {
    return ResponseEntity.ok(SuccessResponse.success(userService.findAll()));
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "User 등록", operationId = "create")
  public ResponseEntity<SuccessResponse<UserDto>> createUser(
      @RequestPart("userCreateRequest") UserPostDto userPostDto,
      @Parameter(description = "User 프로필 이미지") @RequestPart("profile") MultipartFile profile) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(SuccessResponse.success(userService.create(userPostDto, profile)));
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  @Operation(summary = "User 삭제", operationId = "delete")
  public ResponseEntity<?> deleteUser(
      @Parameter(name = "userId", description = "삭제할 User ID") @PathVariable UUID userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }

  // 사용자 정보 수정
  // todo: 수정할 profileImage 필요
  @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "User 정보 수정", operationId = "update")
  public ResponseEntity<SuccessResponse<UserDto>> updateUser(
      @Parameter(name = "userId", description = "수정할 User ID") @PathVariable UUID userId,
      @RequestPart("userUpdateRequest") UserPatchDto userPatchDto,
      @Parameter(name = "profile", description = "수정할 User 프로필 이미지") @RequestPart MultipartFile profile) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(userService.updateUser(userId, userPatchDto, profile)));
  }


}
