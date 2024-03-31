package domain.board;

import db.PositionDto;
import db.SquareDao;
import db.SquareDto;
import domain.piece.Color;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class Squares {

    private static final List<Position> ALL_POSITIONS_CACHE;
    private static final Set<Position> RANK_ONE_POSITIONS_CACHE;

    static {
        ALL_POSITIONS_CACHE = Rank.valuesByOrder()
                .stream()
                .flatMap(rank -> Arrays.stream(File.values())
                        .map(file -> new Position(file, rank)))
                .toList();

        RANK_ONE_POSITIONS_CACHE = Arrays.stream(File.values())
                .map(file -> new Position(file, Rank.ONE))
                .collect(Collectors.toSet());
    }

    private final SquareDao squareDao;
    private final Map<Position, Piece> squares;

    public Squares(Map<Position, Piece> squares) {
        this.squareDao = new SquareDao();
        this.squares = squares;
    }

    public Squares() {
        this.squareDao = new SquareDao();
        this.squares = findSquares();
    }

    private Map<Position, Piece> findSquares() {
        return createPositionDto()
                .stream()
                .map(squareDao::findPieceByPosition)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        squareDto -> new Position(squareDto.positionDto()),
                        squareDto -> Piece.from(squareDto.pieceType(), squareDto.color()),
                        (position, piece) -> position,
                        LinkedHashMap::new
                ));
    }

    private List<PositionDto> createPositionDto() {
        return ALL_POSITIONS_CACHE.stream()
                .map(Position::positionDto)
                .toList();
    }

    public boolean isEmpty() {
        return squares.isEmpty();
    }

    public List<PieceType> pieceTypes(Color color) {
        return squares.values().stream()
                .filter(piece -> piece.isSameColor(color))
                .map(Piece::type)
                .toList();
    }

    public Color findDeadKing() {
        if (!squares.containsValue(new Piece(PieceType.KING, Color.WHITE))) {
            return Color.WHITE;
        }
        if (!squares.containsValue(new Piece(PieceType.KING, Color.BLACK))) {
            return Color.BLACK;
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

    public int countSameFilePawn(Color color) {
        long count = 0;
        for (Position position : RANK_ONE_POSITIONS_CACHE) {
            long countOfSameFile = getPawnCountOfSameFile(color, position);
            count = getPawnCount(countOfSameFile, count);
        }
        return (int) count;
    }

    private long getPawnCountOfSameFile(Color color, Position position) {
        Set<Position> sameFilePositions = position.findSameFilePositions();
        return sameFilePositions.stream()
                .map(squares::get)
                .filter(piece -> piece.isSameColor(color))
                .filter(piece -> piece.isSameType(PieceType.PAWN, PieceType.FIRST_PAWN))
                .count();
    }

    private long getPawnCount(long countOfSameFile, long count) {
        if (countOfSameFile > 1) {
            count += countOfSameFile;
        }
        return count;
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

    public Piece findPieceByPosition(Position position) {
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
