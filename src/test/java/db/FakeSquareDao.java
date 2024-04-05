package db;

import domain.piece.Piece;
import domain.position.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeSquareDao {

    private final Map<Position, Piece> squares;

    public FakeSquareDao() {
        this.squares = new HashMap<>();
    }

    public int addSquares(List<SquareDto> squareDtos) {
        for (SquareDto squareDto : squareDtos) {
            Position position = new Position(squareDto.positionDto().file(), squareDto.positionDto().rank());
            Piece piece = Piece.from(squareDto.pieceDto().pieceType(), squareDto.pieceDto().color());
            squares.put(position, piece);
        }
        return squareDtos.size();
    }

    public List<SquareDto> findAll() {
        List<SquareDto> squareDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> squaresEntry : squares.entrySet()) {
            PositionDto positionDto = squaresEntry.getKey().positionDto();
            PieceDto pieceDto = squaresEntry.getValue().pieceDto();
            squareDtos.add(new SquareDto(pieceDto, positionDto));
        }
        return squareDtos;
    }

    public int deleteAll() {
        int size = squares.size();
        squares.clear();
        return size;
    }
}
