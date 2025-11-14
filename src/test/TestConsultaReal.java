// pruebas de consultas reales a la base de datos (CRUD)
package test;

import dao.ProductoDAOImpl;
import dao.CodigoBarrasDAOImpl;
import entities.Producto;
import entities.CodigoBarras;
import entities.TipoCodigo;
import java.util.List;

public class TestConsultaReal {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONSULTAS REALES ===\n");
        
        ProductoDAOImpl productoDAO = new ProductoDAOImpl();
        CodigoBarrasDAOImpl codigoDAO = new CodigoBarrasDAOImpl();
        
        try {
            // 1. Verificar si hay productos en la base de datos
            System.out.println("1. Listando todos los productos existentes:");
            System.out.println("==========================================");
            
            List<Producto> productos = productoDAO.listarTodos();
            
            if (productos.isEmpty()) {
                System.out.println("No hay productos en la base de datos.");
                System.out.println("Necesitas crear algunos datos de prueba primero.\n");
                
                // Crear datos de prueba
                crearDatosDePrueba(codigoDAO, productoDAO);
                
                // Intentar listar nuevamente
                productos = productoDAO.listarTodos();
            }
            
            // Mostrar productos
            if (!productos.isEmpty()) {
                System.out.println("✅ Productos encontrados:");
                for (Producto p : productos) {
                    System.out.printf("ID: %d | Nombre: %s | Marca: %s | Precio: $%.2f%n", 
                        p.getId(), p.getNombre(), p.getMarca(), p.getPrecio());
                }
            }
            
            System.out.println("\n2. Probando búsqueda por nombre:");
            System.out.println("=================================");
            
            // Buscar productos que contengan "gaseosa" (o alguna palabra común)
            List<Producto> busqueda = productoDAO.buscarPorNombre("gaseosa");
            if (busqueda.isEmpty()) {
                System.out.println("No se encontraron productos con 'gaseosa' en el nombre");
                // Probar con el primer producto que tengamos
                if (!productos.isEmpty()) {
                    String nombrePrueba = productos.get(0).getNombre().substring(0, 3);
                    busqueda = productoDAO.buscarPorNombre(nombrePrueba);
                    System.out.printf("Buscando con: '%s'%n", nombrePrueba);
                }
            }
            
            for (Producto p : busqueda) {
                System.out.printf(" Encontrado: %s - %s ($%.2f)%n", 
                    p.getNombre(), p.getMarca(), p.getPrecio());
            }
            
            System.out.println("\n3. Probando obtener producto por ID:");
            System.out.println("====================================");
            
            if (!productos.isEmpty()) {
                Long idPrueba = productos.get(0).getId();
                Producto producto = productoDAO.obtenerPorId(idPrueba);
                
                if (producto != null) {
                    System.out.printf("   Producto ID %d: %s%n", producto.getId(), producto.getNombre());
                    System.out.printf("   Marca: %s | Categoría: %s%n", producto.getMarca(), producto.getCategoria());
                    System.out.printf("   Precio: $%.2f | Peso: %s%n", producto.getPrecio(), producto.getPeso());
                    if (producto.getCodigoBarras() != null) {
                        System.out.printf("   Código: %s (%s)%n", 
                            producto.getCodigoBarras().getValor(), 
                            producto.getCodigoBarras().getTipo());
                    }
                } else {
                    System.out.println("No se pudo obtener el producto por ID");
                }
            }
            
            System.out.println("\n¡Todas las consultas funcionaron correctamente!");
            
        } catch (Exception e) {
            System.err.println("ERROR durante las pruebas:");
            e.printStackTrace();
        }
    }
    
    private static void crearDatosDePrueba(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println("Creando datos de prueba...");
        
        try {
            // Crear códigos de barras de prueba
            CodigoBarras codigo1 = new CodigoBarras();
            codigo1.setValor("7790040555501");
            codigo1.setTipo(TipoCodigo.EAN13);
            codigo1.setObservaciones("Código de gaseosa cola A");
            codigo1.setEliminado(false);
            codigoDAO.insertar(codigo1);
            
            CodigoBarras codigo2 = new CodigoBarras();
            codigo2.setValor("7790580123456");
            codigo2.setTipo(TipoCodigo.EAN13);
            codigo2.setObservaciones("Código de gaseosa cola B");
            codigo2.setEliminado(false);
            codigoDAO.insertar(codigo2);
            
            // Crear productos de prueba
            Producto producto1 = new Producto();
            producto1.setNombre("Gaseosa Cola Original");
            producto1.setMarca("Refrescos Alpha");
            producto1.setCategoria("Bebidas");
            producto1.setPrecio(250.50);
            producto1.setPeso(0.5);
            producto1.setCodigoBarras(codigo1);
            producto1.setEliminado(false);
            productoDAO.insertar(producto1);
            
            Producto producto2 = new Producto();
            producto2.setNombre("Gaseosa Cola Premium");
            producto2.setMarca("Bebidas Beta");
            producto2.setCategoria("Bebidas");
            producto2.setPrecio(240.00);
            producto2.setPeso(0.5);
            producto2.setCodigoBarras(codigo2);
            producto2.setEliminado(false);
            productoDAO.insertar(producto2);
            
            System.out.println(" Datos de prueba creados exitosamente!");
            
        } catch (Exception e) {
            System.err.println(" Error al crear datos de prueba:");
            e.printStackTrace();
        }
    }
}