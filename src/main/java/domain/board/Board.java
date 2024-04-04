package domain.board;

import db.SquareDto;
import db.TurnDto;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.PieceTypes;
import domain.position.Position;

import java.util.List;
import java.util.Map;

public class Board {

    private final Squares squares;
    private final Turn turn;

    public Board(Map<Position, Piece> squares, Turn turn) {
        this.squares = new Squares(squares);
        this.turn = turn;
    }

    public Board(Map<Position, Piece> squares) {
        this.squares = new Squares(squares);
        this.turn = new Turn();
    }

    public void checkTurn(Position sourcePosition) {
        Piece sourcePiece = squares.findPieceByPosition(sourcePosition);
        sourcePiece.checkSelfTurn(turn);
    }

    public List<PieceType> pieceTypes(Color color) {
        return squares.pieceTypes(color);
    }

    public Color findWinnerColor() {
        return squares.findWinnerColor();
    }

    public boolean checkMove(Position sourcePosition, Position targetPosition) {
        return squares.checkMove(sourcePosition, targetPosition);
    }

    public void move(Position sourcePosition, Position targetPosition) {
        squares.move(sourcePosition, targetPosition);
        turn.swap();
    }

    public boolean isGameOver(Position targetPosition) {
        return squares.isGameOver(targetPosition);
    }

    public List<PieceTypes> findSameFilePieces(Color color) {
        return squares.countSameFilePawn1(color);
    }

    public List<Piece> extractPieces() {
        return squares.extractPieces();
    }

    public List<SquareDto> boardDto() {
        return squares.squaresDto().entrySet().stream()
                .map(squareEntry -> new SquareDto(squareEntry.getValue(), squareEntry.getKey()))
                .toList();
    }

    public TurnDto turnDto() {
        return turn.turnDto();
    }
}
