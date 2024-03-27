package domain.piece;

import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    @Test
    @DisplayName("폰의 목표 위치가 비어있다면 이동 가능하다.")
    void canMove_PawnTargetIsNone_True() {
        domain.piece.Pawn pawn = new domain.piece.Pawn(PieceType.PAWN, Color.BLACK);
        Piece targetPiece = new Piece(PieceType.NONE, Color.NONE);

        assertThat(pawn.canMove(targetPiece)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(names = {"BLACK", "WHITE"})
    @DisplayName("폰의 목표 위치가 비어있지 않다면 이동할 수 없다.")
    void canMove_PawnTargetIsNotNone_False(Color color) {
        domain.piece.Pawn pawn = new domain.piece.Pawn(PieceType.PAWN, Color.BLACK);
        Piece targetPiece = new Piece(PieceType.PAWN, color);

        assertThat(pawn.canMove(targetPiece)).isFalse();
    }

    @Nested
    class WhitePawnTest {

        @Test
        @DisplayName("흰색 폰은 랭크가 높은 쪽으로 이동할 수 있다.")
        void canMove_WhitePawnToHighRank_True() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.WHITE);
            Position source = new Position(File.B, Rank.TWO);
            Position target = new Position(File.B, Rank.THREE);

            assertThat(pawn.canMove(source, target)).isTrue();
        }

        @Test
        @DisplayName("흰색 폰은 랭크가 낮은 쪽으로 이동할 수 없다.")
        void canMove_WhitePawnToLowRank_False() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.WHITE);
            Position source = new Position(File.B, Rank.THREE);
            Position target = new Position(File.B, Rank.TWO);

            assertThat(pawn.canMove(source, target)).isFalse();
        }

        @Test
        @DisplayName("흰색 폰은 출발점이 랭크 2라면 두 칸 전진할 수 있다.")
        void canMove_WhitePawnTwoStepFromRank2_True() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.WHITE);
            Position source = new Position(File.B, Rank.TWO);
            Position target = new Position(File.B, Rank.FOUR);

            assertThat(pawn.canMove(source, target)).isTrue();
        }

        @Test
        @DisplayName("흰색 폰은 출발점이 랭크 2가 아니라면 두 칸 전진할 수 없다.")
        void canMove_WhitePawnTwoStepNotFromRank2_False() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.WHITE);
            Position source = new Position(File.B, Rank.THREE);
            Position target = new Position(File.B, Rank.FIVE);

            assertThat(pawn.canMove(source, target)).isFalse();
        }
    }

    @Nested
    class BlackPawnTest {

        @Test
        @DisplayName("검은색 폰은 랭크가 낮은 쪽으로 이동할 수 있다.")
        void canMove_WhitePawnToHighRank_True() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.BLACK);
            Position source = new Position(File.B, Rank.SEVEN);
            Position target = new Position(File.B, Rank.SIX);

            assertThat(pawn.canMove(source, target)).isTrue();
        }

        @Test
        @DisplayName("검은색 폰은 랭크가 낮은 쪽으로 이동할 수 없다.")
        void canMove_WhitePawnToLowRank_False() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.BLACK);
            Position source = new Position(File.B, Rank.SIX);
            Position target = new Position(File.B, Rank.SEVEN);

            assertThat(pawn.canMove(source, target)).isFalse();
        }

        @Test
        @DisplayName("검은색 폰은 출발점이 랭크 7이면 두 칸 전진할 수 있다.")
        void canMove_WhitePawnTwoStepFromRank2_True() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.BLACK);
            Position source = new Position(File.B, Rank.SEVEN);
            Position target = new Position(File.B, Rank.FIVE);

            assertThat(pawn.canMove(source, target)).isTrue();
        }

        @Test
        @DisplayName("검은색 폰은 출발점이 랭크 7이 아니라면 두 칸 전진할 수 없다.")
        void canMove_WhitePawnTwoStepNotFromRank2_False() {
            Pawn pawn = new Pawn(PieceType.PAWN, Color.BLACK);
            Position source = new Position(File.B, Rank.SIX);
            Position target = new Position(File.B, Rank.FOUR);

            assertThat(pawn.canMove(source, target)).isFalse();
        }
    }
}