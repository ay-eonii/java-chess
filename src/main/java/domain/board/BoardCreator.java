package domain.board;

public class BoardCreator {
    private final SquaresGenerator squaresGenerator = new SquaresGenerator();

    public Board create() {
        Board board = new Board();
        if (board.hasExistingBoard()) {
            return board;
        }
        board = new Board(squaresGenerator.generate());
        board.save();
        return board;
    }
}
