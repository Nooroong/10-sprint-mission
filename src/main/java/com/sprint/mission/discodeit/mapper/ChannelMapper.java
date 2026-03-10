package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.PrivateChannelPostDto;
import com.sprint.mission.discodeit.dto.PublicChannelPostDto;
import com.sprint.mission.discodeit.entity.Channel;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChannelMapper {

    Channel toEntity(PublicChannelPostDto publicChannelPostDto);

    Channel toEntity(PrivateChannelPostDto privateChannelPostDto);

    ChannelDto toDto(Channel channel, Instant lastMessageTime);

}
