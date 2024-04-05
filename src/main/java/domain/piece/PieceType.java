package domain.piece;

import domain.position.Position;

public enum PieceType {

    BISHOP(MoveTactic.DIAGONAL, AttackTactic.DIAGONAL, false),
    KING(MoveTactic.NEIGHBOR, AttackTactic.NEIGHBOR, true),
    KNIGHT(MoveTactic.ONE_STRAIGHT_ONE_DIAGONAL, AttackTactic.ONE_STRAIGHT_ONE_DIAGONAL, false),
    PAWN(MoveTactic.FORWARD_ONE_STRAIGHT, AttackTactic.ONE_DIAGONAL, false),
    FIRST_PAWN(MoveTactic.FORWARD_ONE_OR_TWO_STRAIGHT, AttackTactic.ONE_DIAGONAL, false),
    QUEEN(MoveTactic.STRAIGHT_DIAGONAL, AttackTactic.STRAIGHT_DIAGONAL, false),
    ROOK(MoveTactic.STRAIGHT, AttackTactic.STRAIGHT, false),
    NONE(MoveTactic.STOP, AttackTactic.NOT_ATTACK, false),
    ;

    private final MoveTactic moveTactic;
    private final AttackTactic attackTactic;
    private final boolean isOver;

    PieceType(MoveTactic moveTactic, AttackTactic attackTactic, boolean isOver) {
        this.moveTactic = moveTactic;
        this.attackTactic = attackTactic;
        this.isOver = isOver;
    }

    public boolean canMove(Position source, Position target, Color color) {
        if (this == PAWN || this == FIRST_PAWN) {
            return moveTactic.canMove(source, target) && color.canMove(source, target);
        }
        return moveTactic.canMove(source, target);
    }

    public boolean canAttack(Position source, Position target, Color color) {
        if (this == PAWN || this == FIRST_PAWN) {
            return attackTactic.canAttack(source, target) && color.canMove(source, target);
        }
        return attackTactic.canAttack(source, target);
    }

    public boolean isOver() {
        return this.isOver;
    }
}
