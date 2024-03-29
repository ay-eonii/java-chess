package domain.score;

import domain.piece.Color;

import java.util.Objects;

public class Score {

    private final Color color;
    private final float totalValue;

    public Score(Color color, float totalValue) {
        this.color = color;
        this.totalValue = totalValue;
    }

    public Color color() {
        return color;
    }

    public float totalValue() {
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Float.compare(totalValue, score.totalValue) == 0 && color == score.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, totalValue);
    }
}
