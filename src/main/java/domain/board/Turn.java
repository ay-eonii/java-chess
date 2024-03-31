package domain.board;

import db.TurnDao;
import db.TurnDto;
import domain.piece.Color;

import java.util.Objects;

public class Turn {

    private final TurnDao turnDao;
    private Color color;

    public Turn(Color color) {
        this.turnDao = new TurnDao();
        this.color = color;
    }

    public Turn() {
        this.turnDao = new TurnDao();
        this.color = initColor();
        save();
    }

    private Color initColor() {
        TurnDto turnDto = turnDao.findTurn();
        if (turnDto != null) {
            return turnDto.color();
        }
        return Color.WHITE;
    }

    public void swap() {
        this.color = color.opposite();
    }

    public void validate(Color color) {
        if (this.color != color) {
            throw new IllegalArgumentException(String.format("[ERROR] 현재는 %s 턴입니다.", this.color));
        }
    }

    public void save() {
        turnDao.addTurn(new TurnDto(color));
    }

    public void update() {
        turnDao.updateTurn(new TurnDto(color));
    }

    public void reset() {
        turnDao.deleteAll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return color == turn.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
