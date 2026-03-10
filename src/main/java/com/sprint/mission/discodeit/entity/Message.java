package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Message extends BaseUpdatableEntity {

    @Lob // TEXT type
    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.SET_NULL) // ON DELETE SET NULL
    private User author;

    // todo: 중간 테이블은 어떻게?
    private List<BinaryContent> attachments = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
        updateUpdatedAt(Instant.now());
    }

    public void addAttachment(BinaryContent attachment) {
        attachments.add(attachment);
    }

    @Override
    public String toString() {
        return "{" +
            channel + ">" +
            author + ": " +
            content +
            "(" + super.getUpdatedAt() + ")}";
    }
}
