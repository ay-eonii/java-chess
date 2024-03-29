package db;

import domain.piece.Color;
import domain.piece.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public int addPiece(PieceDto pieceDto) {
        String query = "INSERT INTO pieces VALUES(?, ?, ?, ?)";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceDto.pieceType().name());
            preparedStatement.setString(2, pieceDto.color().name());
            preparedStatement.setString(3, pieceDto.positionDto().file().name());
            preparedStatement.setString(4, pieceDto.positionDto().rank().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceDto findPieceByPosition(PositionDto positionDto) {
        String query = "SELECT * FROM pieces WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionDto.file().name());
            preparedStatement.setString(2, positionDto.rank().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Color color = Color.valueOf(resultSet.getString("color"));
                return new PieceDto(pieceType, color, positionDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int updatePiecePosition(PieceDto pieceDto) {
        String query = "UPDATE pieces SET piece_type = ?, color = ? WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceDto.pieceType().name());
            preparedStatement.setString(2, pieceDto.color().name());
            preparedStatement.setString(3, pieceDto.positionDto().file().name());
            preparedStatement.setString(4, pieceDto.positionDto().rank().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAll() {
        String query = "DELETE FROM pieces";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
