package dao;

import java.util.List;

public interface GenericDAO<T> {
    void insertar(T entidad);
    void actualizar(T entidad);
    void eliminar(Long id);
    T obtenerPorId(Long id);
    List<T> listarTodos();
}

