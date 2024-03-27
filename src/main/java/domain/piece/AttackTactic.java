package domain.piece;

import domain.position.Position;

import java.util.function.BiPredicate;

public enum AttackTactic {

    DIAGONAL(Position::canAttackDiagonal),
    ATTACK((source, target) -> true);
    // ATTACK은 어떤 역할을 하고 있나요?
    // 서로의 Piece색이 다를 때 이동하는 게 ATTACK 아닐까요?

    private final BiPredicate<Position, Position> tactic;

    AttackTactic(BiPredicate<Position, Position> tactic) {
        this.tactic = tactic;
    }

    public boolean canAttack(Position source, Position target) {
        return tactic.test(source, target);
    }
}
