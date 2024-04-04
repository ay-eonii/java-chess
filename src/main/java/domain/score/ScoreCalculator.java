package domain.score;

import domain.board.Board;
import domain.piece.Color;
import domain.piece.PieceTypes;
import domain.piece.PieceValue;

import java.util.List;

public class ScoreCalculator {

    private final PieceValue pieceValue = new PieceValue();

    public Scores sumValues(Board board) {
        Score white = getScore(board, Color.WHITE);
        Score black = getScore(board, Color.BLACK);
        return new Scores(white, black);
    }

    private Score getScore(Board board, Color color) {
        List<PieceTypes> sameFilePieces = board.findSameFilePieces(color);
        float score = sameFilePieces.stream()
                .map(pieceValue::value)
                .reduce(Float::sum)
                .orElse(0f);
        return new Score(color, score);
    }
}
