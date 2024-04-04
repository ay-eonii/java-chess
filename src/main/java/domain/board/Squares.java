package domain.board;

import db.PieceDto;
import db.PositionDto;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.PieceTypes;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class Squares {

    private static final Set<Position> RANK_ONE_POSITIONS_CACHE;

    static {
        RANK_ONE_POSITIONS_CACHE = Arrays.stream(File.values())
                .map(file -> new Position(file, Rank.ONE))
                .collect(Collectors.toSet());
    }

    private final Map<Position, Piece> squares;

    public Squares(Map<Position, Piece> squares) {
        this.squares = squares;
    }

    public List<PieceType> pieceTypes(Color color) {
        return squares.values().stream()
                .filter(piece -> piece.isSameColor(color))
                .map(Piece::type)
                .toList();
    }

    public Color findWinnerColor() {
        if (!squares.containsValue(Piece.from(PieceType.KING, Color.WHITE))) {
            return Color.BLACK;
        }
        if (!squares.containsValue(Piece.from(PieceType.KING, Color.BLACK))) {
            return Color.WHITE;
        }
        return Color.NONE;
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
    }

    public List<PieceTypes> countSameFilePawn1(Color color) {
        List<PieceTypes> pieceOfSameFile = new ArrayList<>();
        for (Position position : RANK_ONE_POSITIONS_CACHE) {
            PieceTypes sameFilePieceTypes = new PieceTypes(findSameFilePieceTypes(color, position));
            pieceOfSameFile.add(sameFilePieceTypes);
        }
        return pieceOfSameFile;
    }

    private List<PieceType> findSameFilePieceTypes(Color color, Position position) {
        Set<Position> sameFilePositions = position.findSameFilePositions();
        return sameFilePositions.stream()
                .map(squares::get)
                .filter(Objects::nonNull)
                .filter(piece -> piece.isSameColor(color))
                .map(Piece::type)
                .toList();
    }

    private boolean canMove(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = findPieceByPosition(sourcePosition);
        Piece targetPiece = findPieceByPosition(targetPosition);
        boolean canMove = sourcePiece.canMove(targetPiece, sourcePosition, targetPosition);
        boolean canAttack = sourcePiece.canAttack(targetPiece, sourcePosition, targetPosition);
        return canMove || canAttack;
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

    public Piece findPieceByPosition(Position position) {
        return squares.get(position);
    }

    private void placePieceByPosition(Piece piece, Position position) {
        Piece notFirstMovePiece = piece.convertNotFirstMovePiece();
        squares.replace(position, notFirstMovePiece);
    }

    private void displacePieceByPosition(Position position) {
        squares.replace(position, Piece.from(PieceType.NONE, Color.NONE));
    }

    public List<Piece> extractPieces() {
        return squares.values().stream().toList();
    }

    public Map<PositionDto, PieceDto> squaresDto() {
        return squares.entrySet().stream()
                .collect(Collectors.toMap(
                        squareEntry -> squareEntry.getKey().positionDto(),
                        squareEntry -> squareEntry.getValue().pieceDto(),
                        (position, piece) -> position,
                        LinkedHashMap::new
                ));
    }

    public boolean isGameOver(Position targetPosition) {
        Piece targetPiece = findPieceByPosition(targetPosition);
        return targetPiece.checkGameOver();
    }
}
