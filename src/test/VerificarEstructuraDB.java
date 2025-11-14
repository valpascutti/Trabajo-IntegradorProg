// inspeccionar la estructura de la base de datos
package test;

import config.ConexionDB;
import java.sql.*;

public class VerificarEstructuraDB {
    
    public static void main(String[] args) {
        System.out.println("=== VERIFICANDO ESTRUCTURA DE LA BASE DE DATOS ===\n");
        
        try (Connection conn = ConexionDB.getConnection()) {
            
            // Verificar tabla producto
            System.out.println("1. Estructura de la tabla 'producto':");
            System.out.println("=====================================");
            mostrarEstructuraTabla(conn, "producto");
            
            System.out.println("\n2. Estructura de la tabla 'codigo_barras':");
            System.out.println("==========================================");
            mostrarEstructuraTabla(conn, "codigo_barras");
            
            System.out.println("\n3. Contenido actual de las tablas:");
            System.out.println("==================================");
            
            // Verificar si hay datos
            System.out.println("Códigos de barras existentes:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM codigo_barras LIMIT 5")) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.printf("  ID: %d | Valor: %s | Tipo: %s%n", 
                        rs.getLong("id"), 
                        rs.getString("valor"), 
                        rs.getString("tipo"));
                }
                if (count == 0) {
                    System.out.println("No hay códigos de barras");
                }
            }
            
            System.out.println("\nProductos existentes:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM producto LIMIT 5")) {
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.printf("  ID: %d | Nombre: %s | Marca: %s | Precio: %.2f%n", 
                        rs.getLong("id"), 
                        rs.getString("nombre"), 
                        rs.getString("marca"), 
                        rs.getDouble("precio"));
                }
                if (count == 0) {
                    System.out.println("No hay productos");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar la base de datos:");
            e.printStackTrace();
        }
    }
    
    private static void mostrarEstructuraTabla(Connection conn, String nombreTabla) throws SQLException {
        String sql = "DESCRIBE " + nombreTabla;
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.printf("%-20s %-15s %-8s %-8s %-15s%n", 
                "Campo", "Tipo", "Null", "Key", "Default");
            System.out.println("-".repeat(70));
            
            while (rs.next()) {
                System.out.printf("%-20s %-15s %-8s %-8s %-15s%n",
                    rs.getString("Field"),
                    rs.getString("Type"),
                    rs.getString("Null"),
                    rs.getString("Key"),
                    rs.getString("Default"));
            }
        }
    }
}