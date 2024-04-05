package domain.piece;

import db.PieceDto;
import domain.board.Turn;
import domain.position.Position;

import java.util.Arrays;
import java.util.Objects;

public class Piece {

    private final PieceType pieceType;
    private final Color color;

    private Piece(PieceType pieceType, Color color) {
        this.pieceType = pieceType;
        this.color = color;
    }

    public static Piece from(PieceType pieceType, Color color) {
        return new Piece(pieceType, color);
    }

    public boolean isSameType(PieceType... pieceTypes) {
        return Arrays.stream(pieceTypes)
                .anyMatch(pieceType -> this.pieceType == pieceType);
    }

    public boolean isNotBlank() {
        return this.pieceType != PieceType.NONE;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    public boolean canMove(Piece targetPiece, Position source, Position target) {
        return color.canMove(targetPiece.color) && pieceType.canMove(source, target, color);
    }

    public boolean canAttack(Piece targetPiece, Position source, Position target) {
        return color.canAttack(targetPiece.color) && pieceType.canAttack(source, target, color);
    }

    public boolean isSameColor(Color color) {
        return this.color == color;
    }

    public void checkSelfTurn(Turn turn) {
        turn.validate(color);
    }

    public Piece convertNotFirstMovePiece() {
        if (this.pieceType == PieceType.FIRST_PAWN) {
            return new Piece(PieceType.PAWN, this.color);
        }
        return this;
    }

    public PieceType type() {
        return pieceType;
    }

    public boolean checkGameOver() {
        return pieceType.isOver();
    }

    public PieceDto pieceDto() {
        return new PieceDto(pieceType, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, color);
    }
}
