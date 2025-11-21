// generar datos de prueba en la base de datos
package test;

import dao.CodigoBarrasDAOImpl;
import dao.ProductoDAOImpl;
import entities.CodigoBarras;
import entities.Producto;
import entities.TipoCodigo;
import java.time.LocalDate; 

public class CrearDatosPrueba {
    
    public static void main(String[] args) {
        System.out.println("=== CREANDO DATOS DE PRUEBA ===\n");
        
        CodigoBarrasDAOImpl codigoDAO = new CodigoBarrasDAOImpl();
        ProductoDAOImpl productoDAO = new ProductoDAOImpl();
        
        try {
            // Crear productos de diferentes categorías
            crearProductosBebidas(codigoDAO, productoDAO);
            crearProductosLacteos(codigoDAO, productoDAO);
            crearProductosPanificacion(codigoDAO, productoDAO);
            crearProductosLimpieza(codigoDAO, productoDAO);
            crearProductosElectronicos(codigoDAO, productoDAO);
            
            System.out.println("\nTodos los datos de prueba creados exitosamente!");
            System.out.println("Total de productos en la base de datos:");
            
            // Mostrar resumen
            mostrarResumen(productoDAO);
            
        } catch (Exception e) {
            System.err.println("Error al crear datos de prueba:");
            e.printStackTrace();
        }
    }
    
    private static void crearProductosBebidas(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println("Creando productos - Bebidas...");
        
        crearProducto(codigoDAO, productoDAO, "7790895001234", TipoCodigo.EAN13, 
            "Gaseosa Cola Original", "Marca Cola A", "Bebidas", 280.50, 0.5, "Gaseosa cola 500ml");
             
        crearProducto(codigoDAO, productoDAO, "7790895005678", TipoCodigo.EAN13,
            "Gaseosa Cola Premium", "Marca Cola B", "Bebidas", 275.00, 0.5, "Gaseosa cola 500ml");
          
        crearProducto(codigoDAO, productoDAO, "7790895009012", TipoCodigo.EAN13,
            "Gaseosa Lima-Limón", "Refrescos Alpha", "Bebidas", 270.00, 0.5, "Gaseosa sabor lima-limón");
            
        crearProducto(codigoDAO, productoDAO, "7790123456789", TipoCodigo.EAN13,
            "Agua Mineral Natural", "Aguas del Valle", "Bebidas", 120.00, 0.5, "Agua mineral sin gas");
    }
    
    private static void crearProductosLacteos(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println(" Creando productos - Lácteos...");
        
        // Leche
        crearProducto(codigoDAO, productoDAO, "7790260123456", TipoCodigo.EAN13,
            "Leche Entera Larga Vida", "Lácteos del Campo", "Lácteos", 450.00, 1.0, "Leche larga vida 1L");
            
        // Yogur
        crearProducto(codigoDAO, productoDAO, "7790260789012", TipoCodigo.EAN13,
            "Yogur Natural Cremoso", "Yogures Premium", "Lácteos", 180.00, 0.175, "Yogur natural cremoso");
            
        // Queso
        crearProducto(codigoDAO, productoDAO, "7790260345678", TipoCodigo.EAN13,
            "Queso Cremoso Artesanal", "Quesería Beta", "Lácteos", 890.00, 0.3, "Queso cremoso horma");
    }
    
    private static void crearProductosPanificacion(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println(" Creando productos - Panificación...");
        
        // Pan lactal
        crearProducto(codigoDAO, productoDAO, "7790350111222", TipoCodigo.EAN13,
            "Pan Lactal Integral", "Panadería Moderna", "Panificación", 320.00, 0.55, "Pan de molde integral");
            
        // Medialunas
        crearProducto(codigoDAO, productoDAO, "7790350333444", TipoCodigo.EAN13,
            "Medialunas Dulces x6", "Panificadora Central", "Panificación", 280.00, 0.3, "Medialunas dulces pack x6");
    }
    
    private static void crearProductosLimpieza(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println(" Creando productos - Limpieza...");
        
        // Detergente
        crearProducto(codigoDAO, productoDAO, "7790410555666", TipoCodigo.EAN13,
            "Detergente Líquido Limón", "Limpieza Total", "Limpieza", 650.00, 0.75, "Detergente líquido 750ml");
            
        // Papel higiénico
        crearProducto(codigoDAO, productoDAO, "7790410777888", TipoCodigo.EAN13,
            "Papel Higiénico Suave x4", "Papelera del Sur", "Limpieza", 420.00, 0.4, "Papel higiénico doble hoja x4 rollos");
    }
    
    private static void crearProductosElectronicos(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO) {
        System.out.println(" Creando productos - Electrónicos...");
        
        // Pilas
        crearProducto(codigoDAO, productoDAO, "123456780", TipoCodigo.EAN8,
            "Pilas AA Alcalinas x4", "Energía Plus", "Electrónicos", 850.00, 0.15, "Pilas alcalinas AA pack x4");
            
        // Cable USB
        crearProducto(codigoDAO, productoDAO, "234567891", TipoCodigo.EAN8,
            "Cable USB-C 1m", "Genérico", "Electrónicos", 1200.00, 0.1, "Cable USB-C a USB-A 1 metro");
    }
    
    private static void crearProducto(CodigoBarrasDAOImpl codigoDAO, ProductoDAOImpl productoDAO, 
                                     String codigoBarras, TipoCodigo tipo, String nombre, String marca, 
                                     String categoria, double precio, Double peso, String observaciones) {
        try {
            // Verificar si el código ya existe
            CodigoBarras codigoExistente = codigoDAO.buscarPorNumero(codigoBarras);
            if (codigoExistente != null) {
                System.out.printf("   ⚠️  Código %s ya existe, saltando %s%n", codigoBarras, nombre);
                return;
            }
            
            // Crear código de barras
            CodigoBarras codigo = new CodigoBarras();
            codigo.setValor(codigoBarras);
            codigo.setTipo(tipo);
            codigo.setFechaAsignacion(java.time.LocalDate.now()); // Agregar fecha actual
            codigo.setObservaciones(observaciones);
            codigo.setEliminado(false);
            codigoDAO.insertar(codigo);
            
            // Crear producto
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setMarca(marca);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);
            producto.setPeso(peso);
            producto.setCodigoBarras(codigo);
            producto.setEliminado(false);
            productoDAO.insertar(producto);
            
            System.out.printf(" %s - $%.2f (Código: %s)%n", nombre, precio, codigoBarras);
            
        } catch (Exception e) {
            System.err.printf(" Error al crear %s: %s%n", nombre, e.getMessage());
            e.printStackTrace(); // Para ver el stack trace completo
        }
    }
    
    private static void mostrarResumen(ProductoDAOImpl productoDAO) {
        try {
            var productos = productoDAO.listarTodos();
            System.out.printf("    Total productos: %d%n", productos.size());
            
            // Contar por categorías
            var categorias = productos.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    Producto::getCategoria, 
                    java.util.stream.Collectors.counting()));
            
            categorias.forEach((categoria, cantidad) -> 
                System.out.printf("    %s: %d productos%n", categoria, cantidad));
                
        } catch (Exception e) {
            System.err.println(" Error al mostrar resumen: " + e.getMessage());
        }
    }
}