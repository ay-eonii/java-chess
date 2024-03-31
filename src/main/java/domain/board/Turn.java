package domain.board;

import domain.piece.Color;

import java.util.Objects;

public class Turn {

    private Color color;

    public Turn(Color color) {
        this.color = color;
    }

    public void swap() {
        this.color = color.opposite();
    }

    public void validate(Color color) {
        if (this.color != color) {
            throw new IllegalArgumentException(String.format("[ERROR] 현재는 %s 턴입니다.", this.color));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return color == turn.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
