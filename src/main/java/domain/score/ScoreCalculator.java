package domain.score;

import domain.piece.PieceType;
import domain.piece.PieceValue;

import java.util.List;

public class ScoreCalculator {
    public static final float PAWN_RATIO = 0.5f;

    private final PieceValue pieceValue = new PieceValue();

    public float sumValues(List<PieceType> pieceTypes, int countSameFilePawn) {
        Float sum = pieceTypes.stream()
                .map(pieceValue::value)
                .reduce(0f, Float::sum);
        return sum - PAWN_RATIO * countSameFilePawn;
    }
}
