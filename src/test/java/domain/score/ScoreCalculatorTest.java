package domain.score;

import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.piece.PieceType.QUEEN;
import static domain.piece.PieceType.ROOK;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreCalculatorTest {

    @Test
    @DisplayName("기물 한 개의 점수를 합산한다.")
    void sumValues_Queen_9() {
        List<PieceType> pieces = List.of(
                QUEEN
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        float totalValue = scoreCalculator.sumValues(pieces);

        assertThat(totalValue).isEqualTo(9f);
    }

    @Test
    @DisplayName("기물 두 개의 점수를 합산한다.")
    void sumValues_Queen_Rook_14() {
        List<PieceType> pieces = List.of(
                QUEEN, ROOK
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        float totalValue = scoreCalculator.sumValues(pieces);

        assertThat(totalValue).isEqualTo(14f);
    }

    @Test
    @DisplayName("같은 기물 두 개의 점수를 합산한다.")
    void sumValues_Rook_Rook_() {
        List<PieceType> pieces = List.of(
                ROOK, ROOK
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        float totalValue = scoreCalculator.sumValues(pieces);

        assertThat(totalValue).isEqualTo(10f);
    }
}
