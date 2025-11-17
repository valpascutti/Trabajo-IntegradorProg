/**
 * EJEMPLO DE USO DE TRANSACCIONES
 * Los DAO deben ofrecer métodos que acepten una Connection externa 
 * (para participar de la misma transacción)
 * 
 * Muestra cómo multiple DAOs pueden participar en una sola transacción,
 * garantizando consistencia de datos mediante commit/rollback.
 */
package test;

import config.ConexionDB;
import dao.ProductoDAOImpl;
import dao.CodigoBarrasDAOImpl;
import entities.Producto;
import entities.CodigoBarras;
import entities.TipoCodigo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Ejemplo de uso de DAOs con transacciones
 */
public class EjemploTransacciones {
    
    public static void main(String[] args) {
        ejemploTransaccion();
    }
    
    /**
     * Ejemplo de transacción que inserta un código de barras y un producto
     * Si alguna operación falla, ambas se revierten
     */
    public static void ejemploTransaccion() {
        Connection conn = null;
        
        try {
            // Obtener conexión y deshabilitar autocommit
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);
            
            // Crear DAOs
            CodigoBarrasDAOImpl codigoBarrasDAO = new CodigoBarrasDAOImpl();
            ProductoDAOImpl productoDAO = new ProductoDAOImpl();
            
            // 1. Insertar código de barras usando la misma conexión
            CodigoBarras codigoBarras = new CodigoBarras();
            codigoBarras.setValor("123456789012");
            codigoBarras.setTipo(TipoCodigo.EAN13);
            codigoBarras.setObservaciones("Código de prueba transaccional");
            codigoBarras.setEliminado(false);
            
            codigoBarrasDAO.insertar(codigoBarras, conn);
            System.out.println("Código de barras insertado con ID: " + codigoBarras.getId());
            
            // 2. Insertar producto usando la misma conexión
            Producto producto = new Producto();
            producto.setNombre("Producto de prueba transaccional");
            producto.setMarca("Marca Test");
            producto.setCategoria("Categoría Test");
            producto.setPrecio(99.99);
            producto.setPeso(1.5);
            producto.setCodigoBarras(codigoBarras);  // Relacionar con el código de barras
            producto.setEliminado(false);
            
            productoDAO.insertar(producto, conn);
            System.out.println("Producto insertado con ID: " + producto.getId());
            
            // 3. Confirmar transacción
            conn.commit();
            System.out.println("Transacción completada exitosamente");
            
        } catch (Exception e) {
            // En caso de error, revertir la transacción
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("Transacción revertida debido a error: " + e.getMessage());
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error al revertir transacción: " + rollbackEx.getMessage());
            }
        } finally {
            // Cerrar conexión
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}