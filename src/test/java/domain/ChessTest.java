package domain;

import domain.piece.Color;
import domain.score.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChessTest {

    @Test
    @DisplayName("특정 진영의 남아있는 기물의 점수를 구한다.")
    void score_White() {
        Chess chess = new Chess();

        Score score = chess.score(Color.WHITE);

        assertThat(score).isEqualTo(new Score(Color.WHITE, 38f));
    }
}
