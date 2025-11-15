package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    void insertar(T entidad);
    void actualizar(T entidad);
    void eliminar(Long id);
    T obtenerPorId(Long id);
    List<T> listarTodos();
    void insertar(T entidad, Connection conn) throws SQLException;
    void actualizar(T entidad, Connection conn) throws SQLException;
    void eliminar(Long id, Connection conn) throws SQLException;
    T obtenerPorId(Long id, Connection conn) throws SQLException;
    List<T> listarTodos(Connection conn) throws SQLException;

}


