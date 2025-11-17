package dao;

import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T> {

    void insertar(T entidad);
    
    void actualizar(T entidad);

    void eliminar(Long id);

    T obtenerPorId(Long id);

    List<T> listarTodos();
    
    // Métodos con Connection externa para transacciones
    void insertar(T entidad, Connection conn);
    
    void actualizar(T entidad, Connection conn);

    void eliminar(Long id, Connection conn);

    T obtenerPorId(Long id, Connection conn);

    List<T> listarTodos(Connection conn);
}
