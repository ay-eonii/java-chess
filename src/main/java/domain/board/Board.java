package domain.board;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;

import java.util.List;
import java.util.Map;

public class Board {

    private final Squares squares;
    private final Turn turn;

    public Board(Map<Position, Piece> squares) {
        this.squares = new Squares(squares);
        this.turn = new Turn(Color.WHITE);
    }

    public Board() {
        this.squares = new Squares();
        this.turn = new Turn(Color.WHITE);
    }

    public boolean hasExistingBoard() {
        return !squares.isEmpty();
    }

    public void checkTurn(Position sourcePosition) {
        Piece sourcePiece = squares.findPieceByPosition(sourcePosition);
        sourcePiece.checkSelfTurn(turn);
    }

    public List<PieceType> pieceTypes(Color color) {
        return squares.pieceTypes(color);
    }

    public Color findDeadKing() {
        return squares.findDeadKing();
    }

    public boolean checkMove(Position sourcePosition, Position targetPosition) {
        return squares.checkMove(sourcePosition, targetPosition);
    }

    public void move(Position sourcePosition, Position targetPosition) {
        squares.move(sourcePosition, targetPosition);
        turn.swap();
    }

    public int countSameFilePawn(Color color) {
        return squares.countSameFilePawn(color);
    }

    public List<Piece> extractPieces() {
        return squares.extractPieces();
    }
}
