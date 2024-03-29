package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PieceDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public void addPiece(PieceDto pieceDto) {
        String query = "INSERT INTO pieces VALUES(?, ?, ?, ?)";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceDto.pieceType().name());
            preparedStatement.setString(2, pieceDto.color().name());
            preparedStatement.setString(3, pieceDto.positionDto().file().name());
            preparedStatement.setString(4, pieceDto.positionDto().rank().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
