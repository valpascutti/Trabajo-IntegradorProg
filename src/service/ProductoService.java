package service;

import entities.Producto;
import java.util.List;

public interface ProductoService {

    void crearProducto(Producto producto);

    Producto obtenerPorId(Long id);

    List<Producto> listarTodos();

    void actualizar(Producto producto);

    void eliminar(Long id);
}

