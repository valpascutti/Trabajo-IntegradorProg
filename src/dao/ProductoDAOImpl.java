package dao;

import entities.Producto;
import entities.CodigoBarras;
import entities.TipoCodigo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductoDAOImpl extends BaseDAO<Producto> implements ProductoDAO {

    // Queries SQL como constantes para facilitar mantenimiento
    private static final String SELECT_BASE = 
        "SELECT p.*, c.id AS cid, c.valor, c.tipo, c.observaciones " +
        "FROM producto p LEFT JOIN codigo_barras c ON p.id_codigo_barras = c.id " +
        "WHERE p.eliminado = false";
    
    private static final String INSERT_SQL = 
        "INSERT INTO producto (nombre, marca, categoria, precio, peso, id_codigo_barras, eliminado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_SQL = 
        "UPDATE producto SET nombre=?, marca=?, categoria=?, precio=?, peso=?, id_codigo_barras=? WHERE id=?";
    
    private static final String DELETE_SQL = 
        "UPDATE producto SET eliminado = true WHERE id = ?";

    @Override
    public void insertar(Producto producto) {
        Long id = ejecutarInsertConId(INSERT_SQL, 
            producto.getNombre(),
            producto.getMarca(),
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getPeso(),
            obtenerIdCodigoBarras(producto),
            producto.getEliminado());
        
        if (id != null) {
            producto.setId(id);
        }
    }

    @Override
    public void actualizar(Producto producto) {
        ejecutarActualizacion(UPDATE_SQL,
            producto.getNombre(),
            producto.getMarca(),
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getPeso(),
            obtenerIdCodigoBarras(producto),
            producto.getId());
    }

    @Override
    public void eliminar(Long id) {
        ejecutarActualizacion(DELETE_SQL, id);
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return ejecutarConsultaUnica(SELECT_BASE + " AND p.id = ?", this::mapear, id);
    }

    @Override
    public List<Producto> listarTodos() {
        return ejecutarConsultaLista(SELECT_BASE, this::mapear);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return ejecutarConsultaLista(SELECT_BASE + " AND p.nombre LIKE ?", this::mapear, "%" + nombre + "%");
    }

    /**
     * Mapea un ResultSet a un objeto Producto completo (con CodigoBarras)
     */
    @Override
    protected Producto mapear(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        
        // Mapeo de campos del producto
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setMarca(rs.getString("marca"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setPeso(rs.getObject("peso") != null ? rs.getDouble("peso") : null);
        producto.setEliminado(rs.getBoolean("eliminado"));

        // Mapeo del código de barras (si existe)
        Long codigoBarrasId = rs.getObject("cid", Long.class);
        if (codigoBarrasId != null && codigoBarrasId > 0) {
            CodigoBarras codigoBarras = new CodigoBarras();
            codigoBarras.setId(codigoBarrasId);
            codigoBarras.setValor(rs.getString("valor"));
            
            String tipoStr = rs.getString("tipo");
            if (tipoStr != null) {
                codigoBarras.setTipo(TipoCodigo.valueOf(tipoStr));
            }
            
            codigoBarras.setObservaciones(rs.getString("observaciones"));
            producto.setCodigoBarras(codigoBarras);
        }

        return producto;
    }

    /**
     * Método auxiliar para obtener el ID del código de barras de forma segura
     */
    private Long obtenerIdCodigoBarras(Producto producto) {
        return (producto.getCodigoBarras() != null && producto.getCodigoBarras().getId() != null) 
            ? producto.getCodigoBarras().getId() 
            : null;
    }
}
