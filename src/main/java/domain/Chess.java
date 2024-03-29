package domain;

import domain.board.Board;
import domain.board.BoardCreator;
import domain.piece.Color;
import domain.piece.PieceType;
import domain.position.Position;
import domain.score.Score;
import domain.score.ScoreCalculator;

import java.util.List;

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

    public Score score(Color color) {
        List<PieceType> pieceTypes = board.pieceTypes(color);
        return new Score(color, scoreCalculator.sumValues(pieceTypes));
    }

    public Board getBoard() {
        return board;
    }
}
