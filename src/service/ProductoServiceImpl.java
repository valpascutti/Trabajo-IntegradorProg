package service;

import dao.ProductoDAO;
import dao.ProductoDAOImpl;
import entities.Producto;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoServiceImpl() {
        this.productoDAO = new ProductoDAOImpl();
    }

    @Override
    public void crearProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null");
        }

        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a cero");
        }

        producto.setEliminado(false);
        productoDAO.insertar(producto);
    }

    @Override
    public Producto obtenerPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }
        return productoDAO.obtenerPorId(id);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    @Override
    public void actualizar(Producto producto) {

        if (producto == null || producto.getId() == null) {
            throw new IllegalArgumentException("El producto o su ID no pueden ser null");
        }

        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a cero");
        }

        productoDAO.actualizar(producto);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }
        productoDAO.eliminar(id);
    }
}
