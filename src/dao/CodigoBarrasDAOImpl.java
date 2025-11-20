package dao;

import entities.CodigoBarras;
import entities.TipoCodigo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CodigoBarrasDAOImpl extends BaseDAO<CodigoBarras> implements CodigoBarrasDAO {

    private static final String SELECT_BASE = "SELECT * FROM codigo_barras WHERE eliminado = false";
    private static final String INSERT_SQL = "INSERT INTO codigo_barras (valor, tipo, fecha_asignacion, observaciones, eliminado) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE codigo_barras SET valor=?, tipo=?, fecha_asignacion=?, observaciones=? WHERE id=?";
    private static final String DELETE_SQL = "UPDATE codigo_barras SET eliminado = true WHERE id = ?";

    @Override
    public void insertar(CodigoBarras cb) {
        Long id = ejecutarInsertConId(INSERT_SQL, 
            cb.getValor(), 
            cb.getTipo().name(), 
            java.sql.Date.valueOf(cb.getFechaAsignacion()),
            cb.getObservaciones(), 
            cb.getEliminado());
        
        if (id != null) {
            cb.setId(id);
        }
    }

    @Override
    public CodigoBarras obtenerPorId(Long id) {
        return ejecutarConsultaUnica(SELECT_BASE + " AND id = ?", this::mapear, id);
    }

    @Override
    public List<CodigoBarras> listarTodos() {
        return ejecutarConsultaLista(SELECT_BASE, this::mapear);
    }

    @Override
    public void eliminar(Long id) {
        ejecutarActualizacion(DELETE_SQL, id);
    }

    @Override
    public void actualizar(CodigoBarras cb) {
        ejecutarActualizacion(UPDATE_SQL, 
            cb.getValor(), 
            cb.getTipo().name(), 
            java.sql.Date.valueOf(cb.getFechaAsignacion()),
            cb.getObservaciones(), 
            cb.getId());
    }

    @Override
    public CodigoBarras buscarPorNumero(String numero) {
        return ejecutarConsultaUnica(SELECT_BASE + " AND valor = ?", this::mapear, numero);
    }

    // Métodos con Connection externa para transacciones
    @Override
    public void insertar(CodigoBarras cb, Connection conn) {
        Long id = ejecutarInsertConId(INSERT_SQL, conn,
            cb.getValor(), 
            cb.getTipo().name(), 
            java.sql.Date.valueOf(cb.getFechaAsignacion()),
            cb.getObservaciones(), 
            cb.getEliminado());
        
        if (id != null) {
            cb.setId(id);
        }
    }

    @Override
    public CodigoBarras obtenerPorId(Long id, Connection conn) {
        return ejecutarConsultaUnica(SELECT_BASE + " AND id = ?", conn, this::mapear, id);
    }

    @Override
    public List<CodigoBarras> listarTodos(Connection conn) {
        return ejecutarConsultaLista(SELECT_BASE, conn, this::mapear);
    }

    @Override
    public void eliminar(Long id, Connection conn) {
        ejecutarActualizacion(DELETE_SQL, conn, id);
    }

    @Override
    public void actualizar(CodigoBarras cb, Connection conn) {
        ejecutarActualizacion(UPDATE_SQL, conn,
            cb.getValor(), 
            cb.getTipo().name(), 
            java.sql.Date.valueOf(cb.getFechaAsignacion()),
            cb.getObservaciones(), 
            cb.getId());
    }

    @Override
    protected CodigoBarras mapear(ResultSet rs) throws SQLException {
        CodigoBarras cb = new CodigoBarras();
        cb.setId(rs.getLong("id"));
        cb.setValor(rs.getString("valor"));
        cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
        
        // Mapear fecha_asignacion
        java.sql.Date sqlDate = rs.getDate("fecha_asignacion");
        if (sqlDate != null) {
            cb.setFechaAsignacion(sqlDate.toLocalDate());
        }
        
        cb.setObservaciones(rs.getString("observaciones"));
        cb.setEliminado(rs.getBoolean("eliminado"));
        return cb;
    }
}
