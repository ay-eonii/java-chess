package controller;

import domain.Chess;
import domain.command.Command;
import domain.command.PlayCommand;
import domain.piece.Color;
import domain.score.Score;
import view.InputView;
import view.OutputView;
import view.mapper.CommandInput;

public class GameMachine {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void start() {
        outputView.printStartNotice();
        Command command = requestStartCommand();
        if (command.isStart()) {
            Chess chess = new Chess();
            outputView.printBoard(chess.getBoard());
            play(chess);
            chess.update();
        }
    }

    private void play(Chess chess) {
        Color deadKingColor = chess.findDeadKing();
        if (deadKingColor != Color.NONE) {
            outputView.printWinner(deadKingColor, deadKingColor.opposite());
            checkScore(chess);
            return;
        }
        PlayCommand playCommand = requestPlayCommand();
        if (playCommand.isMove()) {
            movePieceByCommand(chess, playCommand);
        }
        if (playCommand.isStatus()) {
            checkScore(chess);
            play(chess);
        }
    }

    private void checkScore(Chess chess) {
        Score whiteScore = chess.score(Color.WHITE);
        Score blackScore = chess.score(Color.BLACK);
        outputView.printScore(whiteScore, blackScore);
    }

    private void movePieceByCommand(Chess chess, PlayCommand playCommand) {
        try {
            chess.movePiece(playCommand.sourcePosition(), playCommand.targetPosition());
            outputView.printBoard(chess.getBoard());
            play(chess);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            play(chess);
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
