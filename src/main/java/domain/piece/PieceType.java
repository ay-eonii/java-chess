package domain.piece;

import domain.position.Position;

public enum PieceType {

    BISHOP(MoveTactic.DIAGONAL, AttackTactic.DIAGONAL),
    KING(MoveTactic.NEIGHBOR, AttackTactic.NEIGHBOR),
    KNIGHT(MoveTactic.ONE_STRAIGHT_ONE_DIAGONAL, AttackTactic.ONE_STRAIGHT_ONE_DIAGONAL),
    PAWN(MoveTactic.FORWARD_ONE_STRAIGHT, AttackTactic.ONE_DIAGONAL),
    FIRST_PAWN(MoveTactic.FORWARD_ONE_OR_TWO_STRAIGHT, AttackTactic.ONE_DIAGONAL),
    QUEEN(MoveTactic.STRAIGHT_DIAGONAL, AttackTactic.STRAIGHT_DIAGONAL),
    ROOK(MoveTactic.STRAIGHT, AttackTactic.STRAIGHT),
    NONE(MoveTactic.STOP, AttackTactic.NOT_ATTACK),
    ;

    private final MoveTactic moveTactic;
    private final AttackTactic attackTactic;
    private boolean isDead;

    PieceType(MoveTactic moveTactic, AttackTactic attackTactic) {
        this.moveTactic = moveTactic;
        this.attackTactic = attackTactic;
    }

    public boolean canMove(Position source, Position target) {
        return moveTactic.canMove(source, target);
    }

    public boolean canAttack(Position source, Position target) {
        return attackTactic.canAttack(source, target);
    }

    public void die() {
        this.isDead = true;
    }

    public boolean isFinish() {
        return KING.isDead;
    }
}
