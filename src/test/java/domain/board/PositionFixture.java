package domain.board;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class PositionFixture {

    public static final Position A1 = new Position(File.A, Rank.ONE);
    public static final Position A2 = new Position(File.A, Rank.TWO);
    public static final Position A3 = new Position(File.A, Rank.THREE);
    public static final Position A4 = new Position(File.A, Rank.FOUR);
    public static final Position A5 = new Position(File.A, Rank.FIVE);
    public static final Position A6 = new Position(File.A, Rank.SIX);
    public static final Position A7 = new Position(File.A, Rank.SEVEN);
    public static final Position A8 = new Position(File.A, Rank.EIGHT);
    public static final Position B1 = new Position(File.B, Rank.ONE);
    public static final Position B2 = new Position(File.B, Rank.TWO);
    public static final Position B3 = new Position(File.B, Rank.THREE);
    public static final Position B4 = new Position(File.B, Rank.FOUR);
    public static final Position B5 = new Position(File.B, Rank.FIVE);
    public static final Position B6 = new Position(File.B, Rank.SIX);
    public static final Position B7 = new Position(File.B, Rank.SEVEN);
    public static final Position B8 = new Position(File.B, Rank.EIGHT);
    public static final Position C1 = new Position(File.C, Rank.ONE);
    public static final Position C2 = new Position(File.C, Rank.TWO);
    public static final Position C3 = new Position(File.C, Rank.THREE);
    public static final Position C4 = new Position(File.C, Rank.FOUR);
    public static final Position C5 = new Position(File.C, Rank.FIVE);
    public static final Position C6 = new Position(File.C, Rank.SIX);
    public static final Position C7 = new Position(File.C, Rank.SEVEN);
    public static final Position C8 = new Position(File.C, Rank.EIGHT);
    public static final Position D1 = new Position(File.D, Rank.ONE);
    public static final Position D2 = new Position(File.D, Rank.TWO);
    public static final Position D3 = new Position(File.D, Rank.THREE);
    public static final Position D4 = new Position(File.D, Rank.FOUR);
    public static final Position D5 = new Position(File.D, Rank.FIVE);
    public static final Position D6 = new Position(File.D, Rank.SIX);
    public static final Position D7 = new Position(File.D, Rank.SEVEN);
    public static final Position D8 = new Position(File.D, Rank.EIGHT);
    public static final Position E1 = new Position(File.E, Rank.ONE);
    public static final Position E2 = new Position(File.E, Rank.TWO);
    public static final Position E3 = new Position(File.E, Rank.THREE);
    public static final Position E4 = new Position(File.E, Rank.FOUR);
    public static final Position E5 = new Position(File.E, Rank.FIVE);
    public static final Position E6 = new Position(File.E, Rank.SIX);
    public static final Position E7 = new Position(File.E, Rank.SEVEN);
    public static final Position E8 = new Position(File.E, Rank.EIGHT);
    public static final Position F1 = new Position(File.F, Rank.ONE);
    public static final Position F2 = new Position(File.F, Rank.TWO);
    public static final Position F3 = new Position(File.F, Rank.THREE);
    public static final Position F4 = new Position(File.F, Rank.FOUR);
    public static final Position F5 = new Position(File.F, Rank.FIVE);
    public static final Position F6 = new Position(File.F, Rank.SIX);
    public static final Position F7 = new Position(File.F, Rank.SEVEN);
    public static final Position F8 = new Position(File.F, Rank.EIGHT);
    public static final Position G1 = new Position(File.G, Rank.ONE);
    public static final Position G2 = new Position(File.G, Rank.TWO);
    public static final Position G3 = new Position(File.G, Rank.THREE);
    public static final Position G4 = new Position(File.G, Rank.FOUR);
    public static final Position G5 = new Position(File.G, Rank.FIVE);
    public static final Position G6 = new Position(File.G, Rank.SIX);
    public static final Position G7 = new Position(File.G, Rank.SEVEN);
    public static final Position G8 = new Position(File.G, Rank.EIGHT);
    public static final Position H1 = new Position(File.H, Rank.ONE);
    public static final Position H2 = new Position(File.H, Rank.TWO);
    public static final Position H3 = new Position(File.H, Rank.THREE);
    public static final Position H4 = new Position(File.H, Rank.FOUR);
    public static final Position H5 = new Position(File.H, Rank.FIVE);
    public static final Position H6 = new Position(File.H, Rank.SIX);
    public static final Position H7 = new Position(File.H, Rank.SEVEN);
    public static final Position H8 = new Position(File.H, Rank.EIGHT);
    private static final Set<Position> positions;

    static {
        positions = Set.of(
                A1, A2, A3, A4, A5, A6, A7, A8,
                B1, B2, B3, B4, B5, B6, B7, B8,
                C1, C2, C3, C4, C5, C6, C7, C8,
                D1, D2, D3, D4, D5, D6, D7, D8,
                E1, E2, E3, E4, E5, E6, E7, E8,
                F1, F2, F3, F4, F5, F6, F7, F8,
                G1, G2, G3, G4, G5, G6, G7, G8,
                H1, H2, H3, H4, H5, H6, H7, H8
        );
    }

    public static Set<Position> otherPositions(Position position) {
        Set<Position> allPositions = new HashSet<>(allPositions());
        allPositions.remove(position);
        return allPositions;
    }

    public static Set<Position> otherPositions(Set<Position> positions) {
        Set<Position> allPositions = new HashSet<>(allPositions());
        allPositions.removeAll(positions);
        return allPositions;
    }

    public static Set<Position> otherPositions(List<Position> positions) {
        Set<Position> allPositions = new HashSet<>(allPositions());
        positions.forEach(allPositions::remove);
        return allPositions;
    }

    private static Set<Position> allPositions() {
        return Collections.unmodifiableSet(positions);
    }

    public static Map<Position, Piece> emptySquares() {
        return emptySquares(Map.of(A1, Piece.from(PieceType.NONE, Color.NONE)));
    }

    public static Map<Position, Piece> emptySquares(Map<Position, Piece> squares) {
        Map<Position, Piece> board = allPositions().stream()
                .collect(Collectors.toMap(
                        o -> o,
                        o -> Piece.from(PieceType.NONE, Color.NONE)
                ));
        squares.forEach(board::replace);
        return board;
    }
}
