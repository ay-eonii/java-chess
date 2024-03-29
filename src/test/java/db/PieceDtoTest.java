package db;

import org.junit.jupiter.api.Test;

import static domain.piece.Color.WHITE;
import static domain.piece.PieceType.QUEEN;
import static domain.position.File.A;
import static domain.position.Rank.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class PieceDtoTest {

    private final PieceDao pieceDao = new PieceDao();

    @Test
    void addPosition() {
        PositionDto positionDto = new PositionDto(A, ONE);
        PieceDto pieceDto = new PieceDto(QUEEN, WHITE, positionDto);

        pieceDao.addPiece(pieceDto);
    }

    @Test
    void findPieceByPosition() {
        PositionDto positionDto = new PositionDto(A, ONE);

        PieceDto pieceDto = pieceDao.findPieceByPosition(positionDto);

        assertThat(pieceDto).isEqualTo(new PieceDto(QUEEN, WHITE, positionDto));
    }
}
