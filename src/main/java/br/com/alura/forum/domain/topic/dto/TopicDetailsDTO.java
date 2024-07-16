package br.com.alura.forum.domain.topic.dto;

import br.com.alura.forum.domain.topic.Topic;
import br.com.alura.forum.domain.topic.TopicStatus;

import java.time.Instant;

public record TopicDetailsDTO(Long id,
                              String message,
                              Long topicId,
                              Instant createdAt,
                              String author,
                              TopicStatus status) {

    public TopicDetailsDTO(Topic topic) {
        this(topic.getId(),
                topic.getMessage(),
                topic.getCourse().getId(),
                topic.getCreatedAt(),
                topic.getAuthor().getName(),
                topic.getTopicStatus());
    }
}
