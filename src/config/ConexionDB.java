package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ConexionDB {

    private static Properties dbProperties = null;

    private static void cargarPropiedades() {
        if (dbProperties == null) {
            try {
                dbProperties = new Properties();
                InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("config/db.properties");
                
                // Si no se encuentra en el classpath, intentar rutas relativas
                if (input == null) {
                    String[] posiblesPaths = {
                        "src/config/db.properties",
                        "trabajo-practico-integrador/src/config/db.properties",
                        "./src/config/db.properties"
                    };
                    
                    for (String path : posiblesPaths) {
                        try {
                            input = new FileInputStream(path);
                            System.out.println("Archivo encontrado en: " + path);
                            break;
                        } catch (IOException e) {
                            // Intentar la siguiente ruta
                        }
                    }
                }
                
                if (input == null) {
                    throw new IOException("No se pudo encontrar db.properties en ninguna ubicación");
                }
                
                dbProperties.load(input);
                
                // Cargar el driver de MySQL una sola vez
                Class.forName("com.mysql.cj.jdbc.Driver");

            } catch (IOException e) {
                System.err.println("Error al leer el archivo de propiedades: " + e.getMessage());
                throw new RuntimeException("No se pudieron cargar las propiedades de la BD", e);
            } catch (ClassNotFoundException e) {
                System.err.println("No se encontró el driver de MySQL. Asegúrate de tener el .jar agregado.");
                throw new RuntimeException("Driver MySQL no encontrado", e);
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        cargarPropiedades();
        
        String url = dbProperties.getProperty("DB_URL");
        String user = dbProperties.getProperty("DB_USER");
        String password = dbProperties.getProperty("DB_PASSWORD");

        // Crear una nueva conexión cada vez
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
