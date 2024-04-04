package domain.piece;

import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @ParameterizedTest
    @EnumSource(names = {"A", "C"})
    @DisplayName("흰색 폰은 랭크가 높은 대각선으로 공격할 수 있다.")
    void canAttack_WhitePawnOneDiagonal_True(File file) {
        Piece pawn = Piece.from(PieceType.PAWN, Color.WHITE);
        Piece targetPiece = Piece.from(PieceType.PAWN, Color.BLACK);

        Position source = new Position(File.B, Rank.FIVE);
        Position target = new Position(file, Rank.SIX);

        assertThat(pawn.canAttack(targetPiece, source, target)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(names = {"A", "C"})
    @DisplayName("흰색 폰은 랭크가 낮은 대각선으로 공격할 수 없다.")
    void canAttack_WhitePawnOneDiagonal_False(File file) {
        Piece pawn = Piece.from(PieceType.PAWN, Color.WHITE);
        Piece targetPiece = Piece.from(PieceType.PAWN, Color.BLACK);

        Position source = new Position(File.B, Rank.FIVE);
        Position target = new Position(file, Rank.FOUR);

        assertThat(pawn.canAttack(targetPiece, source, target)).isFalse();
    }

    @ParameterizedTest
    @EnumSource(names = {"A", "C"})
    @DisplayName("검은색 폰은 랭크가 낮은 대각선으로 공격할 수 있다.")
    void canAttack_BlackPawnOneDiagonal_True(File file) {
        Piece pawn = Piece.from(PieceType.PAWN, Color.BLACK);
        Piece targetPiece = Piece.from(PieceType.PAWN, Color.WHITE);

        Position source = new Position(File.B, Rank.FIVE);
        Position target = new Position(file, Rank.FOUR);

        assertThat(pawn.canAttack(targetPiece, source, target)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(names = {"A", "C"})
    @DisplayName("검은색 폰은 랭크가 높은 대각선으로 공격할 수 없다.")
    void canAttack_BlackPawnOneDiagonal_False(File file) {
        Piece pawn = Piece.from(PieceType.PAWN, Color.BLACK);
        Piece targetPiece = Piece.from(PieceType.PAWN, Color.WHITE);

        Position source = new Position(File.B, Rank.FOUR);
        Position target = new Position(file, Rank.FIVE);

        assertThat(pawn.canAttack(targetPiece, source, target)).isFalse();
    }
}
