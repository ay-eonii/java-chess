package domain.piece;

import java.util.Map;

public class PieceValue {

    public static final float RATIO = 0.5f;

    private final Map<PieceType, Float> values = Map.of(
            PieceType.QUEEN, 9f,
            PieceType.ROOK, 5f,
            PieceType.BISHOP, 3f,
            PieceType.KNIGHT, 2.5f,
            PieceType.PAWN, 1f,
            PieceType.FIRST_PAWN, 1f,
            PieceType.KING, 0f
    );

    public float value(PieceType pieceType, boolean isSpecialPawn) {
        if (isSpecialPawn) {
            return values.get(pieceType) * RATIO;
        }
        return values.get(pieceType);
    }

    public float value(PieceType pieceType) {
        return value(pieceType, false);
    }
}
