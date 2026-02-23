package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.UserPatchDto;
import com.sprint.mission.discodeit.dto.UserPostDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.BusinessLogicException;
import com.sprint.mission.discodeit.exception.ExceptionCode;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserMapper userMapper;
  private final BinaryContentMapper binaryContentMapper;
  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final BinaryContentRepository binaryContentRepository;

  @Override
  public UserDto create(UserPostDto userPostDto, MultipartFile profile) {
    // username과 email이 다른 유저와 같으면 안 된다.
    if (isUserNameDuplicated(userPostDto.userName()) ||
        isEmailDuplicated(userPostDto.email())) {
      throw new BusinessLogicException(ExceptionCode.USER_INFO_DUPLICATED, userPostDto.email());
    }

    // 새 user 객체 생성
    User newUser = userMapper.toUser(userPostDto);

    // 프로필 정보를 선택적으로 저장
    File uploadDest = new File(
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images",
            profile.getOriginalFilename()).toString());

    if (!uploadDest.getParentFile().exists()) {
      uploadDest.getParentFile().mkdirs();
    }

    try {
      BinaryContent binaryContent = new BinaryContent(
          newUser.getId(),
          null,
          profile.getOriginalFilename(),
          (int) profile.getSize(),
          profile.getContentType(),
          profile.getBytes()
      );
      binaryContentRepository.save(binaryContent);
      profile.transferTo(new File(uploadDest.toString()));

      newUser.updateProfileId(binaryContent.getId()); // user에 프로필 정보 업데이트
    } catch (IOException e) {
      e.printStackTrace();
      throw new BusinessLogicException(ExceptionCode.ATTACHMENT_SAVE_EXCEPTION);
    }

    // UserStatus를 같이 생성 및 저장
    userStatusRepository.save(new UserStatus(newUser.getId()));

    return userMapper.toUserResponseDto(userRepository.save(newUser),
        getOnlineStatus(newUser.getId()));
  }

  public boolean isUserNameDuplicated(String userName) {
    return userRepository.findAll().stream()
        .anyMatch(user -> user.getUsername().equals(userName));
  }

  public boolean isEmailDuplicated(String email) {
    return userRepository.findAll().stream()
        .anyMatch(user -> user.getEmail().equals(email));
  }

  @Override
  public UserDto findById(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND, userId));
    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND, userId));

    return userMapper.toUserResponseDto(user, getOnlineStatus(userId));
  }

  @Override
  public UserDto findByUserName(String userName) {
    User user = userRepository.findByUserName(userName)
        .orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.USER_NAME_NOT_FOUND, userName)
        );
    UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND, user.getId()));

    return userMapper.toUserResponseDto(user, getOnlineStatus(user.getId()));
  }

  @Override
  public List<UserDto> findAll() {
    // todo: UserStatus의 isLogined를 활용하여 온라인 상태 반환
    return userRepository.findAll().stream()
        .map(user -> userMapper.toUserResponseDto(user, getOnlineStatus(user.getId())))
        .collect(Collectors.toList());
  }

  @Override
  public UserDto updateUser(UUID userId, UserPatchDto userPatchDto, MultipartFile profile) {
    User updatedUser = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND, userId));

    // 유저 정보 업데이트
    Optional.ofNullable(userPatchDto.newUsername())
        .ifPresent(updatedUser::updateNickName);
    Optional.ofNullable(userPatchDto.newEmail())
        .ifPresent(updatedUser::updateEmail);
    Optional.ofNullable(userPatchDto.newPhoneNumber())
        .ifPresent(updatedUser::updatePhoneNumber);
    Optional.ofNullable(userPatchDto.newPassword())
        .ifPresent(updatedUser::updatePassword);

    // todo: binarycontent 업데이트
    File uploadDest = new File(
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images",
            profile.getOriginalFilename()).toString());

    if (!uploadDest.getParentFile().exists()) {
      uploadDest.getParentFile().mkdirs();
    }

    try {
      BinaryContent binaryContent = new BinaryContent(
          updatedUser.getId(),
          null,
          profile.getOriginalFilename(),
          (int) profile.getSize(),
          profile.getContentType(),
          profile.getBytes()
      );
      binaryContentRepository.save(binaryContent);
      profile.transferTo(new File(uploadDest.toString()));

      updatedUser.updateProfileId(binaryContent.getId()); // user에 프로필 정보 업데이트
    } catch (IOException e) {
      e.printStackTrace();
      throw new BusinessLogicException(ExceptionCode.ATTACHMENT_SAVE_EXCEPTION);
    }

    return userMapper.toUserResponseDto(userRepository.save(updatedUser), getOnlineStatus(userId));
  }

  @Override
  public void delete(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND, userId));
    userRepository.delete(userId);

    // 관련된 도메인도 함께 삭제
    userStatusRepository.findByUserId(userId)
        .ifPresent(userStatus -> userStatusRepository.delete(userStatus.getId()));
    Optional.ofNullable(user.getProfileId()).ifPresent(binaryContentRepository::delete);

  }

  private boolean getOnlineStatus(UUID userId) {
    return userStatusRepository.findByUserId(userId)
        .map(UserStatus::isLoggedIn)
        .orElse(false);
  }
}
