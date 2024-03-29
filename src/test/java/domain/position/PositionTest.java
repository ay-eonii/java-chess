package domain.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static domain.position.File.*;
import static domain.position.Rank.*;
import static org.assertj.core.api.Assertions.assertThat;

@Nested
public class PositionTest {
    
    @ParameterizedTest
    @EnumSource(names = {"A", "B", "C", "D", "E", "F", "G", "H"})
    @DisplayName("현재 위치와 파일이 같은 모든 위치를 구한다.")
    void findSameFilePositions(File file) {
        Position position = new Position(file, TWO);

        Set<Position> sameFilePositions = position.findSameFilePositions();

        Assertions.assertThat(sameFilePositions).containsOnly(
                new Position(file, ONE),
                new Position(file, TWO),
                new Position(file, THREE),
                new Position(file, FOUR),
                new Position(file, FIVE),
                new Position(file, SIX),
                new Position(file, SEVEN),
                new Position(file, EIGHT)
        );
    }

    @Nested
    class DiagonalPositionTest {

        private static Stream<Arguments> provideFileAndRank() {
            return Stream.of(
                    Arguments.of(A, ONE),
                    Arguments.of(A, THREE),
                    Arguments.of(C, ONE),
                    Arguments.of(C, THREE),
                    Arguments.of(D, FOUR),
                    Arguments.of(E, FIVE),
                    Arguments.of(F, SIX),
                    Arguments.of(G, SEVEN),
                    Arguments.of(H, EIGHT)
            );
        }

        @ParameterizedTest
        @MethodSource("provideFileAndRank")
        @DisplayName("두 위치가 대각선에 존재하면 참을 반환한다.")
        void isDiagonal_True(File file, Rank rank) {
            Position source = new Position(B, TWO);
            Position target = new Position(file, rank);

            assertThat(source.isDiagonal(target)).isTrue();
        }

        @Test
        @DisplayName("두 위치가 대각선에 존재하지 않으면 거짓을 반환한다.")
        void isDiagonal_False() {
            Position source = new Position(B, TWO);
            Position target = new Position(B, THREE);

            assertThat(source.isDiagonal(target)).isFalse();
        }
    }

    @Nested
    class StraightPositionTest {

        private static Stream<Arguments> provideFileAndRank() {
            return Stream.of(
                    Arguments.of(B, ONE),
                    Arguments.of(B, THREE),
                    Arguments.of(B, FOUR),
                    Arguments.of(B, FIVE),
                    Arguments.of(B, SIX),
                    Arguments.of(B, SEVEN),
                    Arguments.of(B, EIGHT),
                    Arguments.of(A, TWO),
                    Arguments.of(C, TWO),
                    Arguments.of(D, TWO),
                    Arguments.of(E, TWO),
                    Arguments.of(F, TWO),
                    Arguments.of(G, TWO)
            );
        }

        @ParameterizedTest
        @MethodSource("provideFileAndRank")
        @DisplayName("두 위치가 직선에 존재하면 참을 반환한다.")
        void isStraight_True(File file, Rank rank) {
            Position source = new Position(B, TWO);
            Position target = new Position(file, rank);

            assertThat(source.isStraight(target)).isTrue();
        }

        @Test
        @DisplayName("두 위치가 직선에 존재하지 않으면 거짓을 반환한다.")
        void isStraight_False() {
            Position source = new Position(B, TWO);
            Position target = new Position(A, THREE);

            assertThat(source.isStraight(target)).isFalse();
        }
    }

    @Nested
    class StraightDiagonalPositionTest {

        private static Stream<Arguments> provideFileAndRank() {
            return Stream.of(
                    Arguments.of(B, THREE),
                    Arguments.of(B, FIVE),
                    Arguments.of(C, TWO),
                    Arguments.of(C, SIX),
                    Arguments.of(E, TWO),
                    Arguments.of(E, SIX),
                    Arguments.of(F, THREE),
                    Arguments.of(F, FIVE)
            );
        }

        @ParameterizedTest
        @MethodSource("provideFileAndRank")
        @DisplayName("두 위치가 한 칸 직선 한 칸 대각선에 존재하면 참을 반환한다.")
        void isStraightDiagonal_True(File file, Rank rank) {
            Position source = new Position(D, FOUR);
            Position target = new Position(file, rank);

            assertThat(source.isStraightDiagonal(target)).isTrue();
        }

        @Test
        @DisplayName("두 위치가 한 칸 직선 한 칸 대각선에 존재하지 않으면 거짓을 반환한다.")
        void isStraightDiagonal_False() {
            Position source = new Position(D, FOUR);
            Position target = new Position(A, THREE);

            assertThat(source.isStraightDiagonal(target)).isFalse();
        }
    }

    @Nested
    class EveryDirectionPositionTest {

        private static Stream<Arguments> provideFileAndRank() {
            return Stream.of(
                    Arguments.of(C, THREE),
                    Arguments.of(C, FOUR),
                    Arguments.of(C, FIVE),
                    Arguments.of(D, THREE),
                    Arguments.of(D, FIVE),
                    Arguments.of(E, THREE),
                    Arguments.of(E, FOUR),
                    Arguments.of(E, FIVE)
            );
        }

        @ParameterizedTest
        @MethodSource("provideFileAndRank")
        @DisplayName("두 위치가 모든 방향 한 칸 내에 존재하면 참을 반환한다.")
        void isNeighbor_True(File file, Rank rank) {
            Position source = new Position(D, FOUR);
            Position target = new Position(file, rank);

            assertThat(source.isNeighbor(target)).isTrue();
        }

        @Test
        @DisplayName("두 위치가 모든 방향 한 칸 내에 존재하지 않으면 거짓을 반환한다.")
        void isNeighbor_False() {
            Position source = new Position(D, FOUR);
            Position target = new Position(A, THREE);

            assertThat(source.isNeighbor(target)).isFalse();
        }
    }

    @Nested
    class ForwardPositionTest {

        @Test
        @DisplayName("두 위치가 한 칸 앞에 존재하면 참을 반환한다.")
        void isForwardStraight_First_True() {
            Position source = new Position(D, TWO);
            Position target = new Position(D, THREE);

            assertThat(source.isForwardStraight(target)).isTrue();
        }

        @Test
        @DisplayName("두 위치가 한 칸 앞에 존재하지 않으면 거짓을 반환한다.")
        void isForwardStraight_First_False() {
            Position source = new Position(D, FOUR);
            Position target = new Position(D, EIGHT);

            assertThat(source.isForwardStraight(target)).isFalse();
        }

        @Test
        @DisplayName("처음이 아닐 때 두 위치가 한 칸 앞에 존재하면 참을 반환한다.")
        void isForwardStraight_NotFirst_True() {
            Position source = new Position(D, THREE);
            Position target = new Position(D, FOUR);

            assertThat(source.isForwardStraight(target)).isTrue();
        }

        @Test
        @DisplayName("처음이 아닐 때 두 위치가 한 칸 앞에 존재하지 않으면 거짓을 반환한다.")
        void isForwardStraight_NotFirst_False() {
            Position source = new Position(D, THREE);
            Position target = new Position(D, FIVE);

            assertThat(source.isForwardStraight(target)).isFalse();
        }
    }

    @Nested
    class BetweenPositionsTest {

        @Test
        @DisplayName("직선 위 두 위치 사이에 존재하는 위치들을 반환한다.")
        void findBetweenStraightPositions() {
            Position source = new Position(D, FOUR);
            Position target = new Position(H, FOUR);

            List<Position> positions = source.findBetweenStraightPositions(target);

            assertThat(positions).containsExactly(
                    new Position(E, FOUR),
                    new Position(F, FOUR),
                    new Position(G, FOUR)
            );
        }

        @Test
        @DisplayName("대각선 위 두 위치 사이에 존재하는 위치들을 반환한다.")
        void findBetweenDiagonalPositions() {
            Position source = new Position(D, FOUR);
            Position target = new Position(H, EIGHT);

            List<Position> positions = source.findBetweenDiagonalPositions(target);

            assertThat(positions).containsExactly(
                    new Position(E, FIVE),
                    new Position(F, SIX),
                    new Position(G, SEVEN)
            );
        }
    }
}
