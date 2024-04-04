package domain.piece;

import java.util.List;

public class PieceTypes {

    private final List<PieceType> pieceTypes;

    public PieceTypes(List<PieceType> pieceTypes) {
        this.pieceTypes = pieceTypes;
    }

    public List<PieceType> pieceTypes() {
        return pieceTypes;
    }

    public int count(PieceType pieceType) {
        return (int) pieceTypes.stream()
                .filter(type -> type == pieceType)
                .count();
    }
}
