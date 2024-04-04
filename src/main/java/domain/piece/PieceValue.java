package domain.piece;

import java.util.Map;

public class PieceValue {

    public static final float PAWN_RATIO = 0.5f;

    private final Map<PieceType, Float> values = Map.of(
            PieceType.QUEEN, 9f,
            PieceType.ROOK, 5f,
            PieceType.BISHOP, 3f,
            PieceType.KNIGHT, 2.5f,
            PieceType.PAWN, 1f,
            PieceType.FIRST_PAWN, 1f,
            PieceType.KING, 0f
    );

    public float value(PieceType pieceType) {
        return values.get(pieceType);
    }

    public float value(PieceTypes pieceTypes) {
        int pawnCount = pieceTypes.count(PieceType.PAWN);
        float sum = pieceTypes.pieceTypes().stream()
                .map(values::get)
                .reduce(Float::sum)
                .orElse(0f);

        return sum - (PAWN_RATIO * pawnCount);
    }
}
