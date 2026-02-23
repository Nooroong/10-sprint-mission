package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BinaryContentMapper {

  // todo: binarycontentmapper 수정
  public BinaryContent fromDto(UUID userId, UUID messageId, BinaryContentDto binaryContentDtO) {
    return new BinaryContent(
        userId,
        messageId,
        binaryContentDtO.fileName(),
        0,
        "",
        binaryContentDtO.data()
    );
  }

  public BinaryContent fromDto(BinaryContentPostDto binaryContentPostDTO) {
    return new BinaryContent(
        binaryContentPostDTO.userId(),
        binaryContentPostDTO.messageId(),
        binaryContentPostDTO.fileName(),
        0,
        "",
        binaryContentPostDTO.data() // todo
    );
  }

}
