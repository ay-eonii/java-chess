package domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class PieceValueTest {

    @Test
    @DisplayName("Queen의 점수는 9점이다.")
    void score_Queen_9() {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(PieceType.QUEEN);

        assertThat(score).isEqualTo(9f);
    }

    @Test
    @DisplayName("Rook의 점수는 5점이다.")
    void score_Rook_5() {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(PieceType.ROOK);

        assertThat(score).isEqualTo(5f);
    }

    @Test
    @DisplayName("Bishop의 점수는 3점이다.")
    void score_Bishop_3() {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(PieceType.BISHOP);

        assertThat(score).isEqualTo(3f);
    }

    @Test
    @DisplayName("Knight의 점수는 2.5점이다.")
    void score_Knight_2_5() {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(PieceType.KNIGHT);

        assertThat(score).isEqualTo(2.5f);
    }

    @ParameterizedTest
    @EnumSource(names = {"PAWN", "FIRST_PAWN"})
    @DisplayName("초기 Pawn의 점수는 1점이다.")
    void score_FirstPawn_1(PieceType pieceType) {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(pieceType);

        assertThat(score).isEqualTo(1f);
    }

    @Test
    @DisplayName("King의 기본 점수는 0점이다.")
    void score_King_0() {
        PieceValue pieceValue = new PieceValue();

        float score = pieceValue.value(PieceType.KING);

        assertThat(score).isEqualTo(0f);
    }
}
