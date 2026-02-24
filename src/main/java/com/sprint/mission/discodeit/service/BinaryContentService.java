package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.BusinessLogicException;
import com.sprint.mission.discodeit.exception.ExceptionCode;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;

  private final BinaryContentMapper binaryContentMapper;

  public BinaryContent create(BinaryContentPostDto binaryContentPostDto) {
    return binaryContentRepository.save(binaryContentMapper.fromDto(binaryContentPostDto));
  }

  public BinaryContentResponseDto findById(UUID id) throws IOException {
    BinaryContent binaryContent = binaryContentRepository.findById(id)
        .orElseThrow(() ->
            new BusinessLogicException(ExceptionCode.BINARY_CONTENT_NOT_FOUND, id)
        );

    File file = new File(
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images",
            binaryContent.getFileName()).toString());

    byte[] fileBytes = Files.readAllBytes(file.toPath());
    String base64 = Base64.getEncoder().encodeToString(fileBytes);

    return new BinaryContentResponseDto(
        binaryContent.getId(),
        binaryContent.getCreatedAt(),
        binaryContent.getFileName(),
        binaryContent.getSize(),
        "image/" + StringUtils.getFilenameExtension(binaryContent.getFileName()),
        base64
    );
  }

  // todo: return에 dto를 사용
  public List<BinaryContent> findAllByIdIn(List<UUID> idList) {
    return binaryContentRepository.findByIdIn(idList);
  }

  public void delete(UUID id) {
    binaryContentRepository.findById(id)
        .ifPresentOrElse(
            value -> binaryContentRepository.delete(id),
            () -> {
              throw new BusinessLogicException(ExceptionCode.BINARY_CONTENT_NOT_FOUND, id);
            }
        );
  }
}
