package db;

import domain.piece.Color;
import domain.piece.PieceType;
import domain.position.File;
import domain.position.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SquareDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public int addSquares(List<SquareDto> squareDtos) {
        String query = "INSERT INTO squares(piece_type, color, x, y) VALUES(?, ?, ?, ?)";
        try {
            Connection connection = chessDatabase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (SquareDto squareDto : squareDtos) {
                PieceDto pieceDto = squareDto.pieceDto();
                preparedStatement.setString(1, pieceDto.pieceType().name());
                preparedStatement.setString(2, pieceDto.color().name());

                PositionDto positionDto = squareDto.positionDto();
                preparedStatement.setString(3, positionDto.file().name());
                preparedStatement.setString(4, positionDto.rank().name());
                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            }
            int executeCount = preparedStatement.executeBatch().length;
            preparedStatement.clearBatch();
            return executeCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SquareDto> findAll() {
        String query = "SELECT * FROM squares";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SquareDto> squareDtos = new ArrayList<>();
            while (resultSet.next()) {
                File file = File.valueOf(resultSet.getString("x"));
                Rank rank = Rank.valueOf(resultSet.getString("y"));
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Color color = Color.valueOf(resultSet.getString("color"));
                PieceDto pieceDto = new PieceDto(pieceType, color);
                PositionDto positionDto = new PositionDto(file, rank);
                squareDtos.add(new SquareDto(pieceDto, positionDto));
            }
            return squareDtos;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
