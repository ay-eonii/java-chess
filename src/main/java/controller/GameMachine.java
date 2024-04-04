package controller;

import db.ChessDto;
import db.ChessRepository;
import domain.Chess;
import domain.board.GameStatus;
import domain.command.Command;
import domain.command.PlayCommand;
import domain.piece.Color;
import domain.score.Scores;
import view.InputView;
import view.OutputView;
import view.mapper.CommandInput;

import java.util.Optional;

public class GameMachine {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ChessRepository chessRepository = new ChessRepository();

    public void start() {
        outputView.printStartNotice();
        Command command = requestStartCommand();
        if (command.isStart()) {
            GameStatus gameStatus = GameStatus.PLAYING;
            Chess chess = initChess();
            outputView.printBoard(chess.getBoard());
            play(chess, gameStatus);
        }
    }

    private Chess initChess() {
        Optional<ChessDto> chessDto = chessRepository.findChess();
        return chessDto.map(Chess::new)
                .orElseGet(Chess::new);
    }

    private void play(Chess chess, GameStatus gameStatus) {
        if (gameStatus == GameStatus.OVER) {
            Color winnerColor = chess.findWinnerColor();
            outputView.printWinner(winnerColor, winnerColor.opposite());
            checkScore(chess);
            chessRepository.reset();
            return;
        }
        PlayCommand playCommand = requestPlayCommand();
        if (playCommand.isMove()) {
            movePieceByCommand(chess, playCommand);
        }
        if (playCommand.isStatus()) {
            checkScore(chess);
            play(chess, gameStatus);
        }
    }

    private void checkScore(Chess chess) {
        Scores score = chess.score();
        outputView.printScore(score);
    }

    private void movePieceByCommand(Chess chess, PlayCommand playCommand) {
        GameStatus gameStatus = GameStatus.PLAYING;
        try {
            gameStatus = chess.movePiece(playCommand.sourcePosition(), playCommand.targetPosition());
            chessRepository.save(chess.boardDto(), chess.turnDto());
            outputView.printBoard(chess.getBoard());
            play(chess, gameStatus);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            play(chess, gameStatus);
        }
    }

    private Command requestStartCommand() {
        try {
            String command = inputView.readCommand();
            return CommandInput.asStartCommand(command);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return requestStartCommand();
        }
    }

    private PlayCommand requestPlayCommand() {
        try {
            String rawCommand = inputView.readCommand();
            return CommandInput.asPlayCommand(rawCommand);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return requestPlayCommand();
        }
    }
}
