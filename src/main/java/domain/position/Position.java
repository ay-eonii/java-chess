package domain.position;

import db.PositionDto;

import java.util.*;
import java.util.stream.Collectors;

public class Position {

    private static final int ZERO_STEP = 0;
    private static final int ONE_STEP = 1;
    private static final int TWO_STEP = 2;

    private final File file;
    private final Rank rank;

    public Position(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public Position(PositionDto positionDto) {
        this(positionDto.file(), positionDto.rank());
    }

    public int rankDirection(Position target) {
        return rank.forwardDistance(target.rank);
    }

    public boolean isDiagonal(Position target) {
        int fileDistance = file.distance(target.file);
        int rankDistance = rank.distance(target.rank);
        return fileDistance == rankDistance;
    }

    public boolean isDiagonal(Position target, int step) {
        int fileDistance = file.distance(target.file);
        int rankDistance = rank.distance(target.rank);
        return step == fileDistance && fileDistance == rankDistance;
    }

    public boolean isStraight(Position target) {
        return file.isSame(target.file) || rank.isSame(target.rank);
    }

    public boolean isStraightDiagonal(Position target) {
        int fileDistance = file.distance(target.file);
        int rankDistance = rank.distance(target.rank);
        return (isOneStep(fileDistance) && isTwoStep(rankDistance)) || (isTwoStep(fileDistance) && isOneStep(rankDistance));
    }

    public boolean isNeighbor(Position target) {
        int fileDistance = file.distance(target.file);
        int rankDistance = rank.distance(target.rank);
        return isDiagonalNeighbor(fileDistance, rankDistance) || isStraightNeighbor(fileDistance, rankDistance);
    }

    private boolean isDiagonalNeighbor(int fileDistance, int rankDistance) {
        return isOneStep(fileDistance) && isOneStep(rankDistance);
    }

    private boolean isStraightNeighbor(int fileDistance, int rankDistance) {
        return (isNoneStep(fileDistance) && isOneStep(rankDistance)) || (isOneStep(fileDistance) && isNoneStep(rankDistance));
    }

    public boolean isForwardStraight(Position target) {
        return isForwardStraight(target, 1);
    }

    public boolean isForwardStraight(Position target, int... steps) {
        int forwardDistance = rank.distance(target.rank);
        return Arrays.stream(steps)
                .anyMatch(step -> step == forwardDistance && file.isSame(target.file));
    }

    public List<Position> findBetweenStraightPositions(Position target) {
        if (file.isSame(target.file)) {
            return rank.betweenRanks(target.rank).stream()
                    .map(rank -> new Position(file, rank))
                    .toList();
        }
        return file.betweenFiles(target.file).stream()
                .map(file -> new Position(file, rank))
                .toList();
    }

    public List<Position> findBetweenDiagonalPositions(Position target) {
        List<File> files = file.betweenFiles(target.file);
        List<Rank> ranks = rank.betweenRanks(target.rank);

        List<Position> positions = new ArrayList<>();
        for (int index = 0; index < ranks.size(); index++) {
            Position position = new Position(files.get(index), ranks.get(index));
            positions.add(position);
        }
        return positions;
    }

    public Set<Position> findSameFilePositions() {
        return Arrays.stream(Rank.values())
                .map(rank -> new Position(this.file, rank))
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean isNoneStep(int rankDistance) {
        return rankDistance == ZERO_STEP;
    }

    private boolean isOneStep(int fileDistance) {
        return fileDistance == ONE_STEP;
    }

    private boolean isTwoStep(int fileDistance) {
        return fileDistance == TWO_STEP;
    }

    public PositionDto positionDto() {
        return new PositionDto(file, rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}
