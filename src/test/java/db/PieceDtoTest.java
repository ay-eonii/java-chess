package db;

import org.junit.jupiter.api.Test;

import static domain.piece.Color.WHITE;
import static domain.piece.PieceType.QUEEN;
import static domain.position.File.A;
import static domain.position.Rank.ONE;

class PieceDtoTest {

    private final PieceDao pieceDao = new PieceDao();

    @Test
    void addPosition() {
        PositionDto positionDto = new PositionDto(A, ONE);
        PieceDto pieceDto = new PieceDto(QUEEN, WHITE, positionDto);

        pieceDao.addPiece(pieceDto);
    }
}
