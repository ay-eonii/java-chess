package db;

import domain.piece.Color;
import domain.piece.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SquareDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public int addSquare(SquareDto squareDto) {
        String query = "INSERT INTO squares VALUES(?, ?, ?, ?)";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, squareDto.pieceType().name());
            preparedStatement.setString(2, squareDto.color().name());
            preparedStatement.setString(3, squareDto.positionDto().file().name());
            preparedStatement.setString(4, squareDto.positionDto().rank().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SquareDto findPieceByPosition(PositionDto positionDto) {
        String query = "SELECT * FROM squares WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionDto.file().name());
            preparedStatement.setString(2, positionDto.rank().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Color color = Color.valueOf(resultSet.getString("color"));
                return new SquareDto(pieceType, color, positionDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int updateSqaure(SquareDto squareDto) {
        String query = "UPDATE squares SET piece_type = ?, color = ? WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, squareDto.pieceType().name());
            preparedStatement.setString(2, squareDto.color().name());
            preparedStatement.setString(3, squareDto.positionDto().file().name());
            preparedStatement.setString(4, squareDto.positionDto().rank().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAll() {
        String query = "DELETE FROM squares";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
