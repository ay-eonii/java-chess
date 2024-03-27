package domain.board;

public class BoardCreator {
    // 특정 구현에 의존하지 않도록 하면 어떨까요?
    private final SquaresGenerator squaresGenerator = new SquaresGenerator();

    public Board create() {
        return new Board(squaresGenerator.generate());
    }
}
