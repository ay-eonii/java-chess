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
        board = initBoard();
        this.scoreCalculator = new ScoreCalculator();
    }

    private Board initBoard() {
        Board board = new Board();
        if (board.hasExistingBoard()) {
            return board;
        }
        Board newBoard = new BoardCreator().create();
        newBoard.save();
        return newBoard;
    }

    public void movePiece(Position sourcePosition, Position targetPosition) {
        board.checkTurn(sourcePosition);
        if (board.checkMove(sourcePosition, targetPosition)) {
            board.move(sourcePosition, targetPosition);
        }
    }

    public Score score(Color color) {
        List<PieceType> pieceTypes = board.pieceTypes(color);
        int countSameFilePawn = board.countSameFilePawn(color);
        float totalValue = scoreCalculator.sumValues(pieceTypes, countSameFilePawn);
        return new Score(color, totalValue);
    }

    public Color findDeadKing() {
        return board.findDeadKing();
    }

    public Board getBoard() {
        return board;
    }
}
