package br.com.alura.forum.domain.topic.dto;

import br.com.alura.forum.domain.course.Categories;
import jakarta.validation.constraints.NotBlank;

public record CreateTopicDTO(
        @NotBlank String title,
        @NotBlank String message,
        Categories category,
        String status,
        Long authorID
) {}

