package dao;

import entities.CodigoBarras;
import entities.TipoCodigo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CodigoBarrasDAOImpl extends BaseDAO<CodigoBarras> implements CodigoBarrasDAO {

    private static final String SELECT_BASE = "SELECT * FROM codigo_barras WHERE eliminado = false";
    private static final String INSERT_SQL = "INSERT INTO codigo_barras (valor, tipo, observaciones, eliminado) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE codigo_barras SET valor=?, tipo=?, observaciones=? WHERE id=?";
    private static final String DELETE_SQL = "UPDATE codigo_barras SET eliminado = true WHERE id = ?";

    @Override
    public void insertar(CodigoBarras cb) {
        Long id = ejecutarInsertConId(INSERT_SQL, 
            cb.getValor(), 
            cb.getTipo().name(), 
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
            cb.getObservaciones(), 
            cb.getId());
    }

    @Override
    public CodigoBarras buscarPorNumero(String numero) {
        return ejecutarConsultaUnica(SELECT_BASE + " AND valor = ?", this::mapear, numero);
    }

    @Override
    protected CodigoBarras mapear(ResultSet rs) throws SQLException {
        CodigoBarras cb = new CodigoBarras();
        cb.setId(rs.getLong("id"));
        cb.setValor(rs.getString("valor"));
        cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
        cb.setObservaciones(rs.getString("observaciones"));
        cb.setEliminado(rs.getBoolean("eliminado"));
        return cb;
    }
}
