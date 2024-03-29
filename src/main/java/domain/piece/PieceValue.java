package domain.piece;

import java.util.Map;

public class PieceValue {

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
}
