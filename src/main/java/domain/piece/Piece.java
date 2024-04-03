package domain.piece;

import db.PieceDto;
import domain.board.Turn;
import domain.position.Position;

import java.util.Arrays;
import java.util.Objects;

public class Piece {

    protected final PieceType pieceType;
    protected final Color color;

    protected Piece(PieceType pieceType, Color color) {
        this.pieceType = pieceType;
        this.color = color;
    }

    public static Piece from(PieceType pieceType, Color color) {
        if (pieceType.isPawn()) {
            return new Pawn(pieceType, color);
        }
        return new Piece(pieceType, color);
    }

    public static Piece of(Piece piece) {
        if (piece.pieceType.isPawn()) {
            return new Pawn(PieceType.PAWN, piece.color);
        }
        return piece;
    }

    public PieceDto createDto() {
        return new PieceDto(pieceType, color);
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

    public boolean canMove(Piece targetPiece, Position sourcePosition, Position targetPosition) {
        return canMove(targetPiece) && canMove(sourcePosition, targetPosition);
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

    public void die() {
        this.pieceType.die();
    }

    public boolean isFinish() {
        return pieceType.isFinish();
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
