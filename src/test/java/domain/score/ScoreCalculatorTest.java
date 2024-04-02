package domain.score;

import domain.board.Board;
import domain.piece.Color;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static domain.board.PositionFixture.*;
import static domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreCalculatorTest {

    @Test
    @DisplayName("기물 한 개의 점수를 합산한다.")
    void sumValues_Queen_9() {
        Board board = new Board(Map.of(
                A3, Piece.from(QUEEN, Color.WHITE)
        ));
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        Scores scores = scoreCalculator.sumValues(board);

        assertThat(scores.white()).isEqualTo(new Score(Color.WHITE, 9f));
    }

    @Test
    @DisplayName("기물 두 개의 점수를 합산한다.")
    void sumValues_Queen_Rook_14() {
        Board board = new Board(Map.of(
                A3, Piece.from(QUEEN, Color.WHITE),
                A4, Piece.from(ROOK, Color.WHITE)
        ));
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        Scores scores = scoreCalculator.sumValues(board);

        assertThat(scores.white()).isEqualTo(new Score(Color.WHITE, 14f));
    }

    @Test
    @DisplayName("같은 기물 두 개의 점수를 합산한다.")
    void sumValues_Rook_Rook_10() {
        Board board = new Board(Map.of(
                A3, Piece.from(ROOK, Color.WHITE),
                A4, Piece.from(ROOK, Color.WHITE)
        ));
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        Scores scores = scoreCalculator.sumValues(board);

        assertThat(scores.white()).isEqualTo(new Score(Color.WHITE, 10f));
    }

    @Test
    @DisplayName("같은 파일에 폰이 두개라면 개당 0.5점으로 계산한다.")
    void sumValues_Pawn_Pawn_2_2() {
        Board board = new Board(Map.of(
                A3, Piece.from(PAWN, Color.WHITE),
                A4, Piece.from(PAWN, Color.WHITE),
                B7, Piece.from(FIRST_PAWN, Color.WHITE)
        ));
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        Scores scores = scoreCalculator.sumValues(board);

        assertThat(scores.white()).isEqualTo(new Score(Color.WHITE, 2f));
    }
}
