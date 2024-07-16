package br.com.alura.forum.domain.course;

import lombok.Getter;

@Getter
public enum Categories {
    JAVA("Java"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    C("C"),
    RUBY("Ruby");

    private final String displayName;

    Categories(String displayName) {
        this.displayName = displayName;
    }

    public static Categories fromDisplayName(String displayName) {
        for (Categories category : Categories.values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Curso não encontrado: " + displayName);
    }

    public static Categories fromString(String category) {
        for (Categories c : Categories.values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada: " + category);
    }
}
