package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "channels")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Channel extends BaseUpdatableEntity {

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelType type;

    @Column(length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "channel")
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "channel")
    private List<ReadStatus> readStatusList = new ArrayList<>();


    public void updateName(String name) {
        this.name = name;
        updateUpdatedAt(Instant.now());
    }

    public void updateDescription(String description) {
        this.description = description;
        updateUpdatedAt(Instant.now());
    }


    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    @Override
    public String toString() {
        return "Channel{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", messageList=" + messageList +
            ", userStatusList=" + readStatusList +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channel channel)) {
            return false;
        }
        return Objects.equals(this.getId(), channel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
