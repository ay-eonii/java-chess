package db;

import domain.piece.Color;
import domain.piece.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SquareDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public int addSquare(SquareDto squareDto) {
        String query = "INSERT INTO squares VALUES(?, ?, ?, ?)";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            PieceDto pieceDto = squareDto.pieceDto();
            preparedStatement.setString(1, pieceDto.pieceType().name());
            preparedStatement.setString(2, pieceDto.color().name());

            PositionDto positionDto = squareDto.positionDto();
            preparedStatement.setString(3, positionDto.file().name());
            preparedStatement.setString(4, positionDto.rank().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<SquareDto> findPieceByPosition(PositionDto positionDto) {
        String query = "SELECT * FROM squares WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionDto.file().name());
            preparedStatement.setString(2, positionDto.rank().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Color color = Color.valueOf(resultSet.getString("color"));
                PieceDto pieceDto = new PieceDto(pieceType, color);
                return Optional.of(new SquareDto(pieceDto, positionDto));
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int updateSqaure(SquareDto squareDto) {
        String query = "UPDATE squares SET piece_type = ?, color = ? WHERE x = ? AND y = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            PieceDto pieceDto = squareDto.pieceDto();
            preparedStatement.setString(1, pieceDto.pieceType().name());
            preparedStatement.setString(2, pieceDto.color().name());

            PositionDto positionDto = squareDto.positionDto();
            preparedStatement.setString(3, positionDto.file().name());
            preparedStatement.setString(4, positionDto.rank().name());
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
