package domain.board;

import domain.piece.Color;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> squares;
    private final Turn turn;

    public Board(Map<Position, Piece> squares) {
        this.squares = squares;
        this.turn = new Turn(Color.WHITE);
    }

    public void checkTurn(Position sourcePosition) {
        Piece sourcePiece = findPieceByPosition(sourcePosition);
        sourcePiece.checkSelfTurn(turn);
    }

    public boolean checkMove(Position sourcePosition, Position targetPosition) {
        if (isBlocked(sourcePosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
        }
        if (canMove(sourcePosition, targetPosition)) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
    }

    public void move(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = findPieceByPosition(sourcePosition);
        placePieceByPosition(sourcePiece, targetPosition);
        displacePieceByPosition(sourcePosition);
        turn.swap();
    }

    private boolean canMove(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = findPieceByPosition(sourcePosition);
        Piece targetPiece = findPieceByPosition(targetPosition);
        return sourcePiece.canMove(targetPiece, sourcePosition, targetPosition)
                || sourcePiece.canAttack(targetPiece, sourcePosition, targetPosition);
    }

    private boolean isBlocked(Position source, Position target) {
        List<Position> betweenPositions = new ArrayList<>();
        if (source.isStraight(target)) {
            betweenPositions.addAll(source.findBetweenStraightPositions(target));
        }
        if (source.isDiagonal(target)) {
            betweenPositions.addAll(source.findBetweenDiagonalPositions(target));
        }
        return betweenPositions.stream()
                .map(this::findPieceByPosition)
                .anyMatch(Piece::isNotBlank);
    }

    private Piece findPieceByPosition(Position position) {
        return squares.get(position);
    }

    private void placePieceByPosition(Piece piece, Position position) {
        Piece changedPiece = piece;
        if (piece.isSameType(PieceType.FIRST_PAWN)) {
            changedPiece = new Pawn(piece);
        }
        squares.replace(position, changedPiece);
    }

    private void displacePieceByPosition(Position position) {
        squares.replace(position, new Piece(PieceType.NONE, Color.NONE));
    }

    public List<Piece> extractPieces() {
        return squares.values().stream().toList();
    }
}
