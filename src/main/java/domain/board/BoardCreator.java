package domain.board;

import db.SquareDto;
import db.TurnDto;
import domain.piece.Piece;
import domain.position.Position;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardCreator {
    private final SquaresGenerator squaresGenerator = new SquaresGenerator();

    public Board create() {
        return new Board(squaresGenerator.generate());
    }

    public Board create(List<SquareDto> squareDtos, TurnDto turnDto) {
        Map<Position, Piece> squares = squareDtos.stream()
                .collect(Collectors.toMap(
                        dto -> new Position(dto.positionDto().file(), dto.positionDto().rank()),
                        dto -> Piece.from(dto.pieceDto().pieceType(), dto.pieceDto().color()),
                        (position, piece) -> position,
                        LinkedHashMap::new
                ));
        return new Board(squares, new Turn(turnDto.color()));
    }
}
