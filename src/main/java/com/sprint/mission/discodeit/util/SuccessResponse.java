package com.sprint.mission.discodeit.util;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

// todo: 모든 컨트롤러에 공통 response 적용
@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

  private String message; // todo: default value를 가지고 선택적으로 지정할 수 있도록 하기
  private T data;
  private Instant timestamp;

  public static <T> SuccessResponse<T> success(T data) {
    return new SuccessResponse<>("요청에 성공하였습니다.", data, Instant.now());
  }

  public static <T> SuccessResponse<T> failure(String message) {
    return new SuccessResponse<>(message, null, Instant.now());
  }
}
