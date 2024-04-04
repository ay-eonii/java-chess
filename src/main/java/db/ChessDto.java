package db;

import java.util.List;

public record ChessDto(List<SquareDto> squareDto, TurnDto turnDto) {
}
