package domain.piece;

import domain.position.Position;

import java.util.Objects;

public class Piece {

    protected final PieceType pieceType;
    protected final Color color;

    public Piece(PieceType pieceType, Color color) {
        this.pieceType = pieceType;
        this.color = color;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    public boolean canMove(Position source, Position target) {
        return pieceType.canMove(source, target);
    }

    public boolean canMove(Piece targetPiece) {
        return this.color != targetPiece.color;
    }

    public boolean canAttack(Position source, Position target) {
        return pieceType.canAttack(source, target);
    }

    public boolean isOpposite(Piece targetPiece) {
        return color != Color.NONE && color != targetPiece.color;
    }

    // equals & hashcode를 재정의한 이유가 있을까요?
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
