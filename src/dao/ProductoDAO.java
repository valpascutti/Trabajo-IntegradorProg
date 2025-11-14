package dao;

import entities.Producto;
import java.util.List;

public interface ProductoDAO extends GenericDAO<Producto> {
    List<Producto> buscarPorNombre(String nombre);
}

