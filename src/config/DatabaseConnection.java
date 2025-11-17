package config;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {
        // Evita instanciación
    }

    public static Connection getConnection() throws SQLException {
        return ConexionDB.getConnection();
    }
    
    public static void closeConnection(Connection connection) {
        ConexionDB.closeConnection(connection);
    }
}
