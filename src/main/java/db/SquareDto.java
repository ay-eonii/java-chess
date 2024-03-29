package db;

import domain.piece.Color;
import domain.piece.PieceType;

public record SquareDto(PieceType pieceType, Color color, PositionDto positionDto) {
}
