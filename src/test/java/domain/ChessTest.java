package domain;

import domain.piece.Color;
import domain.score.Score;
import domain.score.Scores;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChessTest {

    @Test
    @DisplayName("특정 진영의 남아있는 기물의 점수를 구한다.")
    void score_White() {
        Chess chess = new Chess();

        Scores scores = chess.score();

        assertThat(scores.white()).isEqualTo(new Score(Color.WHITE, 38f));
    }
}
