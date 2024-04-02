package domain.board;

import db.PieceDto;
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
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(
                        squareDto -> new Position(squareDto.positionDto()),
                        squareDto -> {
                            PieceDto pieceDto = squareDto.pieceDto();
                            return Piece.from(pieceDto.pieceType(), pieceDto.color());
                        },
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
                .filter(Objects::nonNull)
                .filter(piece -> piece.isSameColor(color))
                .filter(piece -> piece.getClass() == Pawn.class)
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
        Piece changedPiece = Piece.of(piece);
        squares.replace(position, changedPiece);
    }

    private void displacePieceByPosition(Position position) {
        squares.replace(position, Piece.from(PieceType.NONE, Color.NONE));
    }

    public List<Piece> extractPieces() {
        return squares.values().stream().toList();
    }

    public void save() {
        for (Map.Entry<Position, Piece> square : squares.entrySet()) {
            PositionDto positionDto = square.getKey().positionDto();
            PieceDto pieceDto = square.getValue().createDto();
            SquareDto squareDto = new SquareDto(pieceDto, positionDto);
            squareDao.addSquare(squareDto);
        }
    }

    public void update() {
        for (Position position : ALL_POSITIONS_CACHE) {
            PositionDto positionDto = position.positionDto();
            PieceDto pieceDto = squares.get(position).createDto();
            SquareDto squareDto = new SquareDto(pieceDto, positionDto);
            squareDao.updateSqaure(squareDto);
        }
    }

    public void reset() {
        squareDao.deleteAll();
    }
}
