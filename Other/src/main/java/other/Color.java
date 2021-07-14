package other;

import lombok.Getter;

@Getter
public enum Color {
    YELLOW("YELLOW"),
    ORANGE("ORANGE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private String description;

    Color(String description) {
        this.description = description;
    }

}