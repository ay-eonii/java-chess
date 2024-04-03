package domain;

import domain.board.Board;
import domain.board.BoardCreator;
import domain.piece.Color;
import domain.position.Position;
import domain.score.ScoreCalculator;
import domain.score.Scores;

public class Chess {

    private final Board board;
    private final ScoreCalculator scoreCalculator;

    public Chess() {
        board = new BoardCreator().create();
        this.scoreCalculator = new ScoreCalculator();
    }

    public void movePiece(Position sourcePosition, Position targetPosition) {
        board.checkTurn(sourcePosition);
        if (board.checkMove(sourcePosition, targetPosition)) {
            board.move(sourcePosition, targetPosition);
        }
    }

    public Scores score() {
        return scoreCalculator.sumValues(board);
    }

    public Color findWinnerColor() {
        return board.findWinnerColor();
    }

    public boolean isFinish() {
        return board.isFinish();
    }

    public Board getBoard() {
        return board;
    }

    public void update() {
        board.update();
    }

    public void reset() {
        board.reset();
    }

    public void save() {
        board.reset();
        board.save();
    }
}
