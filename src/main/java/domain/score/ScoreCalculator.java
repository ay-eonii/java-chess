package domain.score;

import domain.piece.PieceType;
import domain.piece.PieceValue;

import java.util.List;

public class ScoreCalculator {

    private final PieceValue pieceValue = new PieceValue();

    public float sumValues(List<PieceType> pieceTypes) {
        return pieceTypes.stream()
                .map(pieceValue::value)
                .reduce(0f, Float::sum);
    }
}
