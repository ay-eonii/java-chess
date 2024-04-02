package domain.score;

import domain.board.Board;
import domain.piece.Color;
import domain.piece.PieceType;
import domain.piece.PieceValue;

import java.util.List;

public class ScoreCalculator {
    public static final float PAWN_RATIO = 0.5f;

    private final PieceValue pieceValue = new PieceValue();

    public Scores sumValues(Board board) {
        Score white = getScore(board, Color.WHITE);
        Score black = getScore(board, Color.BLACK);
        return new Scores(white, black);
    }

    private Score getScore(Board board, Color color) {
        List<PieceType> pieceTypes = board.pieceTypes(color);
        int countSameFilePawn = board.countSameFilePawn(color);
        float score = calculate(pieceTypes) - (PAWN_RATIO * countSameFilePawn);
        return new Score(color, score);
    }

    private float calculate(List<PieceType> pieceTypes) {
        return pieceTypes.stream()
                .map(pieceValue::value)
                .reduce(0f, Float::sum);
    }
}
