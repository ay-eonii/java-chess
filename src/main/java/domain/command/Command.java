package domain.command;

public enum Command {

    START,
    MOVE,
    END,
    ;

    public boolean isStart() {
        return this == START;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isEnd() {
        return this == END;
    }
    // command.isStart() 와 command == Command.START는 어떤 차이가 있나요?
    // 명령어가 늘어나면 메소드 수도 계속 증가하게 되나요?
}
