package domain.score;

public class Scores {
    private final Score white;
    private final Score black;

    public Scores(Score white, Score black) {
        this.white = white;
        this.black = black;
    }

    public Score white() {
        return white;
    }

    public Score black() {
        return black;
    }
}
