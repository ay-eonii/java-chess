package domain.board;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static domain.board.PositionFixture.*;
import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {

    private Board board;

    @Test
    @DisplayName("특정 진영의 모든 기물 타입을 구한다.")
    void pieceTypes_White() {
        board = new Board(PositionFixture.emptySquares(Map.of(
                D4, Piece.from(QUEEN, WHITE),
                A4, Piece.from(KING, WHITE),
                C4, Piece.from(ROOK, WHITE),
                C3, Piece.from(ROOK, WHITE),
                D5, Piece.from(ROOK, BLACK)
        )));

        List<PieceType> pieceTypes = board.pieceTypes(WHITE);

        assertThat(pieceTypes).containsOnly(QUEEN, KING, ROOK, ROOK);
    }

//    @Test
//    @DisplayName("세로줄에 같은 색의 폰의 개수를 구한다.")
//    void countSameFilePawn_White_2() {
//        board = new Board(PositionFixture.emptySquares(Map.of(
//                A3, Piece.from(PAWN, WHITE),
//                A4, Piece.from(FIRST_PAWN, WHITE),
//                C4, Piece.from(PAWN, WHITE),
//                A1, Piece.from(KING, WHITE)
//        )));
//
//        long count = board.countSameFilePawn(WHITE);
//
//        assertThat(count).isEqualTo(2);
//    }

    @Test
    @DisplayName("킹이 잡힌 진영을 찾는다.")
    void checkDeadKing() {
        board = new Board(PositionFixture.emptySquares(Map.of(
                D4, Piece.from(QUEEN, WHITE),
                A4, Piece.from(KING, WHITE),
                C4, Piece.from(ROOK, WHITE),
                C3, Piece.from(ROOK, WHITE),
                D5, Piece.from(ROOK, BLACK)
        )));

        Color winnerColor = board.findWinnerColor();

        assertThat(winnerColor).isEqualTo(WHITE);
    }

    @Nested
    class RookTest {

        private static Set<Position> rookPositions() {
            return Set.of(
                    A4, B4, C4, E4, F4, G4, H4,
                    D1, D2, D3, D5, D6, D7, D8
            );
        }

        private static Set<Position> notRookPositions() {
            return otherPositions(rookPositions());
        }

        @BeforeEach
        void setUp() {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(ROOK, WHITE))));
        }

        /**
         * ...*....
         * ...*....
         * ...*....
         * ***r****
         * ...*....
         * ...*....
         * ...*....
         */
        @ParameterizedTest
        @MethodSource("rookPositions")
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_Rook_True(Position targetPosition) {
            boolean canMove = board.checkMove(D4, targetPosition);

            assertThat(canMove).isTrue();
        }

        /**
         * ***.****
         * ***.****
         * ***.****
         * ...r....
         * ***.****
         * ***.****
         * ***.****
         */
        @ParameterizedTest
        @MethodSource("notRookPositions")
        @DisplayName("불가능한 움직임이라면 예외가 발생한다.")
        void canMove_Rook_ThrownException(Position invalidPosition) {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(ROOK, WHITE))));

            assertThatThrownBy(() -> board.checkMove(D4, invalidPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        /**
         * ........
         * ........
         * ........
         * ...rR*..
         * ........
         * ........
         * ........
         */
        @Test
        @DisplayName("가능한 움직임이지만 장애물이 있다면 예외가 발생한다.")
        void canMove_RookBlocked_ThrownException() {
            board = new Board(PositionFixture.emptySquares(Map.of(
                    D4, Piece.from(ROOK, WHITE),
                    E4, Piece.from(ROOK, BLACK)
            )));

            assertThatThrownBy(() -> board.checkMove(D4, F4))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class KnightTest {

        private static Set<Position> knightPositions() {
            return Set.of(B3, C2, B5, F5, C6, E6, E2, F3);
        }

        private static Set<Position> notKnightPositions() {
            return otherPositions(knightPositions());
        }

        @BeforeEach
        void setUp() {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(KNIGHT, WHITE))));
        }

        /**
         * ........
         * ........
         * ..*.*...
         * .*...*..
         * ...n....
         * .*...*..
         * ..*.*...
         * ........
         */
        @ParameterizedTest
        @MethodSource("knightPositions")
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_Knight_True(Position targetPosition) {
            boolean canMove = board.checkMove(D4, targetPosition);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ..*.*...
         * .*NNN*..
         * ..NnN...
         * .*NNN*..
         * ..*.*...
         * ........
         */
        @ParameterizedTest
        @MethodSource("knightPositions")
        @DisplayName("가능한 움직임이라면 장애물이 있어도 이동할 수 있다.")
        void canMove_KnightJump_True(Position targetPosition) {
            board = new Board(PositionFixture.emptySquares(Map.of(
                    D4, Piece.from(KNIGHT, WHITE),
                    C3, Piece.from(KNIGHT, BLACK),
                    C4, Piece.from(KNIGHT, BLACK),
                    C5, Piece.from(KNIGHT, BLACK),
                    D3, Piece.from(KNIGHT, BLACK),
                    D5, Piece.from(KNIGHT, BLACK),
                    E3, Piece.from(KNIGHT, BLACK),
                    E4, Piece.from(KNIGHT, BLACK),
                    E6, Piece.from(KNIGHT, BLACK)
            )));
            boolean canMove = board.checkMove(D4, targetPosition);

            assertThat(canMove).isTrue();
        }

        /**
         * ********
         * ********
         * **.*.***
         * *.***.**
         * ***n***
         * *.***.**
         * **.*.***
         * ********
         */
        @ParameterizedTest
        @MethodSource("notKnightPositions")
        @DisplayName("불가능한 움직임이라면 예외가 발생한다.")
        void canMove_Knight_ExceptionThrown(Position targetPosition) {
            assertThatThrownBy(() -> board.checkMove(D4, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class PawnTest {

        private static List<List<Position>> whitePawnPositions() {
            return List.of(
                    List.of(D4, D5),
                    List.of(D4, D6)
            );
        }

        private static List<List<Position>> blackPawnPositions() {
            return List.of(
                    List.of(D6, D5),
                    List.of(D6, D4)
            );
        }

        /**
         * ........
         * ........
         * ...*....
         * ...*....
         * ...p....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @MethodSource("whitePawnPositions")
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_FirstWhitePawn_True(List<Position> positions) {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(FIRST_PAWN, WHITE))));
            Position sourcePosition = positions.get(0);
            Position targetPosition = positions.get(1);

            boolean canMove = board.checkMove(sourcePosition, targetPosition);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ........
         * ...*....
         * ...p....
         * ........
         * ........
         * ........
         */
        @Test
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_WhitePawn_True() {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(PAWN, WHITE))));

            boolean canMove = board.checkMove(D4, D5);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ...*....
         * ........
         * ...p....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @MethodSource("whitePawnPositions")
        @DisplayName("장애물이 없지만 불가능한 움직임이라면 예외가 발생한다.")
        void canMove_WhitePawn_False() {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(PAWN, WHITE))));

            assertThatThrownBy(() -> board.checkMove(D4, D6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        /**
         * ........
         * ........
         * ...P....
         * ........
         * ...p....
         * ........
         * ........
         * ........
         */
        @Test
        @DisplayName("가능한 움직임이지만 장애물이 있다면 예외가 발생한다.")
        void canMove_FirstWhitePawn_ThrownException() {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(FIRST_PAWN, WHITE))));

            boolean canMove = board.checkMove(D4, D6);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ........
         * ...P....
         * ...p....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @EnumSource(names = {"PAWN", "FIRST_PAWN"})
        @DisplayName("가능한 움직임이지만 장애물이 있다면 예외가 발생한다.")
        void canMove_WhitePawn_ThrownException(PieceType pawn) {
            board = new Board(PositionFixture.emptySquares(Map.of(D4, Piece.from(pawn, WHITE))));

            boolean canMove = board.checkMove(D4, D5);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ...p....
         * ...*....
         * ...*....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @MethodSource("blackPawnPositions")
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_FirstBlackPawn_True(List<Position> positions) {
            board = new Board(PositionFixture.emptySquares(Map.of(D6, Piece.from(FIRST_PAWN, BLACK))));
            Position sourcePosition = positions.get(0);
            Position targetPosition = positions.get(1);

            boolean canMove = board.checkMove(sourcePosition, targetPosition);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ........
         * ...p....
         * ...*....
         * ........
         * ........
         * ........
         */
        @Test
        @DisplayName("장애물이 없고 가능한 움직임이라면 이동할 수 있다.")
        void canMove_BlackPawn_True() {
            board = new Board(PositionFixture.emptySquares(Map.of(D6, Piece.from(PAWN, BLACK))));

            boolean canMove = board.checkMove(D6, D5);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ...p....
         * ........
         * ...*....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @MethodSource("blackPawnPositions")
        @DisplayName("장애물이 없지만 불가능한 움직임이라면 예외가 발생한다.")
        void canMove_BlackPawn_False() {
            board = new Board(PositionFixture.emptySquares(Map.of(D6, Piece.from(PAWN, BLACK))));

            assertThatThrownBy(() -> board.checkMove(D6, D4))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        /**
         * ........
         * ........
         * ...P....
         * ........
         * ...p....
         * ........
         * ........
         * ........
         */
        @Test
        @DisplayName("가능한 움직임이지만 장애물이 있다면 예외가 발생한다.")
        void canMove_FirstBlackPawn_ThrownException() {
            board = new Board(PositionFixture.emptySquares(Map.of(D6, Piece.from(FIRST_PAWN, BLACK))));

            boolean canMove = board.checkMove(D6, D4);

            assertThat(canMove).isTrue();
        }

        /**
         * ........
         * ........
         * ........
         * ...P....
         * ...p....
         * ........
         * ........
         * ........
         */
        @ParameterizedTest
        @EnumSource(names = {"PAWN", "FIRST_PAWN"})
        @DisplayName("가능한 움직임이지만 장애물이 있다면 예외가 발생한다.")
        void canMove_BlackPawn_ThrownException(PieceType pawn) {
            board = new Board(PositionFixture.emptySquares(Map.of(D6, Piece.from(pawn, BLACK))));

            boolean canMove = board.checkMove(D6, D5);

            assertThat(canMove).isTrue();
        }
    }
}
