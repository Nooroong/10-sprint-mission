package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageResponseDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

  private final BinaryContentRepository binaryContentRepository;

  // todo: authroeId와 channelId 추가 필요
  public Message toMessage(MessagePostDto messagePostDto) {
    return new Message(
        messagePostDto.authorId(),
        messagePostDto.channelId(),
        messagePostDto.content(),
        null
    );
  }

  public MessageResponseDto toResponse(Message message) {
    return new MessageResponseDto(
        message.getId(),
        message.getContent(),
        message.getAuthorId(),
        message.getChannelId()
    );
  }

}
