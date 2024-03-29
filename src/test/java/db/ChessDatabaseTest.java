package db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ChessDatabaseTest {

    private final ChessDatabase chessDatabase = new ChessDatabase();

    @Test
    public void connection() throws SQLException {
        try (Connection connection = chessDatabase.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
