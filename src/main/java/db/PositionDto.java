package db;

import domain.position.File;
import domain.position.Rank;

public record PositionDto(File file, Rank rank) {
}
