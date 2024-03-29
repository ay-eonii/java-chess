package domain.piece;

import domain.board.Turn;
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

    public boolean isNotBlank() {
        return this.pieceType != PieceType.NONE;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    public boolean canMove(Piece targetPiece, Position sourcePosition, Position targetPosition) {
        return this.color != targetPiece.color && pieceType.canMove(sourcePosition, targetPosition);
    }

    protected boolean canMove(Position source, Position target) {
        return pieceType.canMove(source, target);
    }

    protected boolean canMove(Piece targetPiece) {
        return this.color != targetPiece.color;
    }

    public boolean canAttack(Piece targetPiece, Position sourcePosition, Position targetPosition) {
        return isOpposite(targetPiece) && canAttack(sourcePosition, targetPosition);
    }

    public boolean canAttack(Position source, Position target) {
        return pieceType.canAttack(source, target) && color.canMove(source, target);
    }

    public boolean isOpposite(Piece targetPiece) {
        return color != Color.NONE && color != targetPiece.color;
    }

    public boolean isSameColor(Color color) {
        return this.color == color;
    }

    public void checkSelfTurn(Turn turn) {
        turn.validate(color);
    }

    public PieceType type() {
        return pieceType;
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
