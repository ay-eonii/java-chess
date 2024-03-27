package domain;

import domain.board.Board;
import domain.board.BoardCreator;
import domain.board.Turn;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;

public class Chess {

    private final Board board;
    private final Turn turn;

    public Chess() {
        board = new BoardCreator().create();
        turn = new Turn(Color.WHITE);
    }

    public void movePiece(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = board.findPieceByPosition(sourcePosition);
        sourcePiece.checkSelfTurn(turn);
        if (canMove(sourcePosition, targetPosition)) {
            board.placePieceByPosition(sourcePiece, targetPosition);
            board.displacePieceByPosition(sourcePosition);
            turn.swap();
        }
    }

    private boolean canMove(Position sourcePosition, Position targetPosition) {
        if (board.canMove(sourcePosition, targetPosition) || board.canAttack(sourcePosition, targetPosition)
                && board.isNotBlocked(sourcePosition, targetPosition)) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
    }

    public Board getBoard() {
        return board;
    }
}
