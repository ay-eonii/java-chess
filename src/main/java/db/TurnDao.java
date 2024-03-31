package db;

import domain.piece.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    public int addTurn(TurnDto turnDto) {
        String query = "INSERT INTO turn VALUES (?)";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnDto.color().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TurnDto findTurn() {
        String query = "SELECT * FROM turn limit 1";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Color color = Color.valueOf(resultSet.getString("color"));
                return new TurnDto(color);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int updateTurn(TurnDto turnDto) {
        String query = "UPDATE turn SET color = ?";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnDto.color().name());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAll() {
        String query = "DELETE FROM turn";
        try (Connection connection = chessDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
