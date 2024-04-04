package domain;

import db.ChessDto;
import db.SquareDto;
import db.TurnDto;
import domain.board.Board;
import domain.board.BoardCreator;
import domain.board.GameStatus;
import domain.piece.Color;
import domain.position.Position;
import domain.score.ScoreCalculator;
import domain.score.Scores;

import java.util.List;

public class Chess {

    private final Board board;
    private final ScoreCalculator scoreCalculator;

    public Chess() {
        board = new BoardCreator().create();
        this.scoreCalculator = new ScoreCalculator();
    }

    public Chess(ChessDto chessDto) {
        board = new BoardCreator().create(chessDto.squareDto(), chessDto.turnDto());
        this.scoreCalculator = new ScoreCalculator();
    }

    public GameStatus movePiece(Position sourcePosition, Position targetPosition) {
        board.checkTurn(sourcePosition);
        if (board.checkMove(sourcePosition, targetPosition)) {
            GameStatus over = checkGameOver(targetPosition);
            board.move(sourcePosition, targetPosition);
            return over;
        }
        return GameStatus.PLAYING;
    }

    private GameStatus checkGameOver(Position targetPosition) {
        if (board.isGameOver(targetPosition)) {
            return GameStatus.OVER;
        }
        return GameStatus.PLAYING;
    }

    public Scores score() {
        return scoreCalculator.sumValues(board);
    }

    public Color findWinnerColor() {
        return board.findWinnerColor();
    }

    public Board getBoard() {
        return board;
    }

    public List<SquareDto> boardDto() {
        return board.boardDto();
    }

    public TurnDto turnDto() {
        return board.turnDto();
    }
}
