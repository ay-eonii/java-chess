package db;

import domain.piece.Color;
import domain.piece.PieceType;

public record PieceDto(PieceType pieceType, Color color) {
}
