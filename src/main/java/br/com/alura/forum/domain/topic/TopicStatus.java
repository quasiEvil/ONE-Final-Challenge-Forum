package br.com.alura.forum.domain.topic;

import lombok.Getter;

@Getter
public enum TopicStatus {
    OPEN("Aberto"),
    SOLVED("Solucionado");

        private final String displayName;

        TopicStatus(String displayName) {
            this.displayName = displayName;
        }
}
