package domain.piece;

import domain.position.Position;

import java.util.function.BiPredicate;

public enum AttackTactic {

    DIAGONAL((source, target) -> source.isDiagonal(target, 1)),
    NOT_ATTACK((source, target) -> false);

    private final BiPredicate<Position, Position> tactic;

    AttackTactic(BiPredicate<Position, Position> tactic) {
        this.tactic = tactic;
    }

    public boolean canAttack(Position source, Position target) {
        return tactic.test(source, target);
    }
}
