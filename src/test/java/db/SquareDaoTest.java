package db;

import org.junit.jupiter.api.*;

import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static domain.piece.PieceType.KING;
import static domain.piece.PieceType.QUEEN;
import static domain.position.File.A;
import static domain.position.Rank.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SquareDaoTest {

    private final SquareDao squareDao = new SquareDao();

    @Test
    @Order(1)
    @DisplayName("기물과 위치를 저장한다.")
    void addPosition() {
        PositionDto positionDto = new PositionDto(A, ONE);
        PieceDto pieceDto = new PieceDto(QUEEN, WHITE);
        SquareDto squareDto = new SquareDto(pieceDto, positionDto);

        int queryCount = squareDao.addSquare(squareDto);

        assertThat(queryCount).isEqualTo(1);

    }

    @Test
    @Order(2)
    @DisplayName("위치로 기물을 찾는다.")
    void findPieceByPosition() {
        PositionDto positionDto = new PositionDto(A, ONE);

        SquareDto squareDto = squareDao.findPieceByPosition(positionDto).get();

        PieceDto pieceDto = new PieceDto(QUEEN, WHITE);
        assertThat(squareDto).isEqualTo(new SquareDto(pieceDto, positionDto));
    }

    @Test
    @Order(3)
    @DisplayName("특정 위치의 기물을 변경한다.")
    void updatePiecePosition() {
        PositionDto positionDto = new PositionDto(A, ONE);
        PieceDto pieceDto = new PieceDto(KING, BLACK);
        SquareDto squareDto = new SquareDto(pieceDto, positionDto);

        int queryCount = squareDao.updateSqaure(squareDto);

        assertThat(queryCount).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("모든 기물과 위치를 삭제한다.")
    void deleteAll() {
        int queryCount = squareDao.deleteAll();

        assertThat(queryCount).isEqualTo(1);
    }
}
