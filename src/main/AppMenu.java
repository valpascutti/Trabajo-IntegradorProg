package main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import entities.CodigoBarras;
import entities.Producto;
import entities.TipoCodigo;
import service.CodigoBarrasService;
import service.CodigoBarrasServiceImpl;
import service.ProductoService;
import service.ProductoServiceImpl;

public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final CodigoBarrasService cbService = new CodigoBarrasServiceImpl();
    private final ProductoService productoService = new ProductoServiceImpl();

    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestión de Códigos de Barras");
            System.out.println("2. Gestión de Productos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuCodigosBarras();
                case 2 -> menuProductos();
                case 0 -> System.out.println("Saliendo... ¡Gracias por usar el sistema!");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---------------------------------------------------------------
    //                  SUBMENÚ CÓDIGOS DE BARRAS
    // ---------------------------------------------------------------

    private void menuCodigosBarras() {
        int opcion;

        do {
            System.out.println("\n--- Gestión de Códigos de Barras ---");
            System.out.println("1. Crear código de barras");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Buscar por número");
            System.out.println("4. Listar todos");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearCodigoBarras();
                case 2 -> buscarCodigoPorId();
                case 3 -> buscarCodigoPorNumero();
                case 4 -> listarCodigos();
                case 5 -> eliminarCodigo();
                case 0 -> {}
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearCodigoBarras() {
        System.out.println("\n--- Crear Código de Barras ---");
        System.out.print("Número de código: ");
        String valor = scanner.nextLine();

        System.out.print("Tipo (EAN13, EAN8, UPC): ");
        TipoCodigo tipo = TipoCodigo.valueOf(scanner.nextLine());

        CodigoBarras cb = new CodigoBarras();
        cb.setValor(valor);
        cb.setTipo(tipo);
        cb.setFechaAsignacion(LocalDate.now());
        cb.setObservaciones("Generado desde AppMenu");
        cb.setEliminado(false);

        cbService.crearCodigoBarras(cb);
        System.out.println("✔ Código creado con ID: " + cb.getId());
    }

    private void buscarCodigoPorId() {
        System.out.print("\nIngrese ID: ");
        Long id = leerLong();
        CodigoBarras cb = cbService.obtenerPorId(id);

        if (cb != null) System.out.println(cb);
        else System.out.println("⚠ No encontrado.");
    }

    private void buscarCodigoPorNumero() {
        System.out.print("\nIngrese número del código: ");
        String numero = scanner.nextLine();
        CodigoBarras cb = cbService.buscarPorNumero(numero);

        if (cb != null) System.out.println(cb);
        else System.out.println("⚠ No encontrado.");
    }

    private void listarCodigos() {
        List<CodigoBarras> lista = cbService.listarTodos();
        System.out.println("\n--- LISTA DE CÓDIGOS ---");
        lista.forEach(System.out::println);
    }

    private void eliminarCodigo() {
        System.out.print("\nID a eliminar: ");
        Long id = leerLong();
        cbService.eliminar(id);
        System.out.println("✔ Código marcado como eliminado.");
    }

    // ---------------------------------------------------------------
    //                          SUBMENÚ PRODUCTOS
    // ---------------------------------------------------------------

    private void menuProductos() {
        int opcion;

        do {
            System.out.println("\n--- Gestión de Productos ---");
            System.out.println("1. Crear producto");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Listar todos");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearProducto();
                case 2 -> buscarProductoPorId();
                case 3 -> listarProductos();
                case 4 -> eliminarProducto();
                case 0 -> {}
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearProducto() {
        System.out.println("\n--- Crear Producto ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = leerDouble();

        System.out.print("Peso (ej: 0.500): ");
        Double peso = leerDouble();

        System.out.print("ID de Código de Barras (opcional, ENTER para ninguno): ");
        String input = scanner.nextLine();

        CodigoBarras cb = null;
        if (!input.isEmpty()) {
            cb = cbService.obtenerPorId(Long.parseLong(input));
            if (cb == null) System.out.println("⚠ Código no encontrado, se crea sin CB.");
        }

        Producto p = new Producto(null, false, nombre, marca, categoria, precio, peso, cb);
        productoService.crearProducto(p);

        System.out.println("✔ Producto creado con ID: " + p.getId());
    }

    private void buscarProductoPorId() {
        System.out.print("\nID del producto: ");
        Long id = leerLong();
        Producto p = productoService.obtenerPorId(id);

        if (p != null) System.out.println(p);
        else System.out.println("⚠ No encontrado.");
    }

    private void listarProductos() {
        List<Producto> lista = productoService.listarTodos();
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        lista.forEach(System.out::println);
    }

    private void eliminarProducto() {
        System.out.print("\nID del producto a eliminar: ");
        Long id = leerLong();
        productoService.eliminar(id);
        System.out.println("✔ Producto eliminado.");
    }

    // ---------------------------------------------------------------
    //                    MÉTODOS DE LECTURA SEGURA
    // ---------------------------------------------------------------

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private long leerLong() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
