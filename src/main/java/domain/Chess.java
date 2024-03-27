package domain;

import domain.board.Board;
import domain.board.BoardCreator;
import domain.position.Position;

public class Chess {

    private final Board board;

    public Chess() {
        board = new BoardCreator().create();
    }

    public void movePiece(Position sourcePosition, Position targetPosition) {
        board.checkTurn(sourcePosition);
        if (board.checkMove(sourcePosition, targetPosition)) {
            board.move(sourcePosition, targetPosition);
        }
    }
    
    public Board getBoard() {
        return board;
    }
}
