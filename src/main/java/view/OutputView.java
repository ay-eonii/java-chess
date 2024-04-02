package view;

import domain.board.Board;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.score.Score;
import domain.score.Scores;

import java.util.Arrays;
import java.util.List;

public class OutputView {

    public void printStartNotice() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printBoard(Board board) {
        List<Piece> pieces = board.extractPieces();
        for (int i = 0; i < pieces.size(); i++) {
            String piece = PieceOutput.asString(pieces.get(i));
            System.out.print(piece);
            separateLineByFileIndex(i);
        }
        System.out.println();
    }

    private void separateLineByFileIndex(int fileIndex) {
        if (isLastFile(fileIndex)) {
            System.out.println();
        }
    }

    private boolean isLastFile(int fileIndex) {
        return fileIndex % 8 == 7;
    }

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printScore(Scores scores) {
        System.out.println("-------- 점수 --------");
        System.out.println(ScoreOutput.asString(scores.white()));
        System.out.println(ScoreOutput.asString(scores.black()));
        System.out.println();
    }

    public void printWinner(Color winnerColor, Color loserColor) {
        System.out.println(loserColor + " 킹이 잡혔습니다.");
        System.out.println("우승 진영은 " + winnerColor + "입니다.");
    }

    private enum PieceOutput {

        BISHOP(List.of(PieceType.BISHOP), "B"),
        KING(List.of(PieceType.KING), "K"),
        KNIGHT(List.of(PieceType.KNIGHT), "N"),
        PAWN(List.of(PieceType.FIRST_PAWN, PieceType.PAWN), "P"),
        QUEEN(List.of(PieceType.QUEEN), "Q"),
        ROOK(List.of(PieceType.ROOK), "R"),
        NONE(List.of(PieceType.NONE), ".");

        private final List<PieceType> pieceTypes;
        private final String output;

        PieceOutput(List<PieceType> pieceTypes, String output) {
            this.pieceTypes = pieceTypes;
            this.output = output;
        }

        private static String asString(Piece piece) {
            String output = Arrays.stream(values())
                    .filter(pieceOutput -> isAnyMatch(piece, pieceOutput.pieceTypes))
                    .findFirst()
                    .orElse(NONE)
                    .output;
            if (piece.isWhite()) {
                return output.toLowerCase();
            }
            return output;
        }

        private static boolean isAnyMatch(Piece piece, List<PieceType> pieceTypes) {
            return pieceTypes.stream()
                    .anyMatch(piece::isSameType);
        }
    }

    private enum ScoreOutput {
        WHITE_SCORE(Color.WHITE, "흰색(소문자) = %.1f점"),
        BLACK_SCORE(Color.BLACK, "검은색(대문자) = %.1f점");

        private final Color color;
        private final String format;

        ScoreOutput(Color color, String format) {
            this.color = color;
            this.format = format;
        }

        private static String asString(Score score) {
            return Arrays.stream(values())
                    .filter(scoreOutput -> scoreOutput.color == score.color())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 색입니다."))
                    .format.formatted(score.totalValue());
        }
    }
}
