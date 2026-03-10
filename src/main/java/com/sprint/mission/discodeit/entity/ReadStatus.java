package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "read_statuses")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ReadStatus extends BaseUpdatableEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    private Instant lastReadAt;

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateLastReadTime(Instant lastReadTime) {
        this.lastReadAt = lastReadTime;
    }
}