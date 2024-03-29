package domain.command;

public enum Command {

    START,
    MOVE,
    END,
    STATUS;

    public boolean isStart() {
        return this == START;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isStatus() {
        return this == STATUS;
    }
}
