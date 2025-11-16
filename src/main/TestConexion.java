package main;

import config.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;

// Clase temporal para probar la conexión a la db
public class TestConexion {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionDB.getConnection();
            if (conn != null) {
                System.out.println("✅ Conexión exitosa a la base de datos!");
                System.out.println("URL: " + conn.getMetaData().getURL());
                System.out.println("Usuario: " + conn.getMetaData().getUserName());
                ConexionDB.closeConnection(conn);
                System.out.println("✅ Conexión cerrada correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
           // e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
           // e.printStackTrace();
        }
    }
}
