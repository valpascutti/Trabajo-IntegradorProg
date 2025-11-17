package dao;

import config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta para DAOs que proporciona funcionalidades comunes
 * y reduce la duplicación de código
 */
public abstract class BaseDAO<T> {

    /**
     * Interfaz funcional que permite SQLException en el mapeo
     */
    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    /**
     * Ejecuta una consulta que retorna un único resultado
     */
    protected T ejecutarConsultaUnica(String sql, ResultSetMapper<T> mapper, Object... parametros) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper.map(rs) : null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta única: " + sql, e);
        }
    }

    /**
     * Ejecuta una consulta que retorna múltiples resultados
     */
    protected List<T> ejecutarConsultaLista(String sql, ResultSetMapper<T> mapper, Object... parametros) {
        List<T> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapper.map(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta de lista: " + sql, e);
        }
        return resultados;
    }

    /**
     * Ejecuta una operación de actualización (INSERT, UPDATE, DELETE)
     */
    protected int ejecutarActualizacion(String sql, Object... parametros) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            return ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en actualización: " + sql, e);
        }
    }

    /**
     * Ejecuta un INSERT y retorna el ID generado
     */
    protected Long ejecutarInsertConId(String sql, Object... parametros) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            setParametros(ps, parametros);
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getLong(1) : null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en inserción: " + sql, e);
        }
    }

    /**
     * Ejecuta una consulta que retorna un único resultado con Connection externa
     */
    protected T ejecutarConsultaUnica(String sql, Connection conn, ResultSetMapper<T> mapper, Object... parametros) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper.map(rs) : null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta única: " + sql, e);
        }
    }

    /**
     * Ejecuta una consulta que retorna múltiples resultados con Connection externa
     */
    protected List<T> ejecutarConsultaLista(String sql, Connection conn, ResultSetMapper<T> mapper, Object... parametros) {
        List<T> resultados = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapper.map(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta de lista: " + sql, e);
        }
        return resultados;
    }

    /**
     * Ejecuta una operación de actualización con Connection externa
     */
    protected int ejecutarActualizacion(String sql, Connection conn, Object... parametros) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setParametros(ps, parametros);
            return ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en actualización: " + sql, e);
        }
    }

    /**
     * Ejecuta un INSERT y retorna el ID generado con Connection externa
     */
    protected Long ejecutarInsertConId(String sql, Connection conn, Object... parametros) {
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            setParametros(ps, parametros);
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getLong(1) : null;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error en inserción: " + sql, e);
        }
    }

    /**
     * Establece los parámetros del PreparedStatement
     */
    private void setParametros(PreparedStatement ps, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i + 1, parametros[i]);
        }
    }

    /**
     * Método abstracto que debe implementar cada DAO para el mapeo específico
     */
    protected abstract T mapear(ResultSet rs) throws SQLException;
}