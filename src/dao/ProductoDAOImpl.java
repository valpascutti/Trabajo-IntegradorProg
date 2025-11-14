package dao;

import config.ConexionDB;
import entities.Producto;
import entities.CodigoBarras;
import entities.TipoCodigo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void insertar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, marca, categoria, precio, peso, id_codigo_barras, eliminado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getMarca());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            if (producto.getPeso() != null) {
                ps.setDouble(5, producto.getPeso());
            } else {
                ps.setNull(5, Types.DOUBLE);
            }
            ps.setLong(6, producto.getCodigoBarras().getId());
            ps.setBoolean(7, producto.getEliminado());

            ps.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar producto", e);
        }
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, marca=?, categoria=?, precio=?, peso=?, id_codigo_barras=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getMarca());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setObject(5, producto.getPeso(), Types.DOUBLE);
            ps.setLong(6, producto.getCodigoBarras().getId());
            ps.setLong(7, producto.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    @Override
    public Producto obtenerPorId(Long id) {
        String sql = "SELECT p.*, c.id AS cid, c.valor, c.tipo, c.observaciones " +
                     "FROM producto p LEFT JOIN codigo_barras c ON p.id_codigo_barras = c.id " +
                     "WHERE p.id = ? AND p.eliminado = false";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getLong("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setMarca(rs.getString("marca"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setPeso(rs.getObject("peso") != null ? rs.getDouble("peso") : null);
                    p.setEliminado(rs.getBoolean("eliminado"));

                    CodigoBarras cb = new CodigoBarras();
                    cb.setId(rs.getLong("cid"));
                    cb.setValor(rs.getString("valor"));
                    cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
                    cb.setObservaciones(rs.getString("observaciones"));
                    p.setCodigoBarras(cb);

                    return p;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener producto", e);
        }
        return null;
    }



    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.valor, c.tipo FROM producto p " +
                     "LEFT JOIN codigo_barras c ON p.id_codigo_barras = c.id WHERE p.eliminado = false";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPeso(rs.getObject("peso") != null ? rs.getDouble("peso") : null);
                p.setEliminado(rs.getBoolean("eliminado"));

                CodigoBarras cb = new CodigoBarras();
                cb.setValor(rs.getString("valor"));
                cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
                p.setCodigoBarras(cb);

                productos.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos", e);
        }
        return productos;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cid, c.valor, c.tipo, c.observaciones " +
                     "FROM producto p LEFT JOIN codigo_barras c ON p.id_codigo_barras = c.id " +
                     "WHERE p.nombre LIKE ? AND p.eliminado = false";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getLong("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setMarca(rs.getString("marca"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setPeso(rs.getObject("peso") != null ? rs.getDouble("peso") : null);
                    p.setEliminado(rs.getBoolean("eliminado"));

                    CodigoBarras cb = new CodigoBarras();
                    cb.setId(rs.getLong("cid"));
                    cb.setValor(rs.getString("valor"));
                    cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
                    cb.setObservaciones(rs.getString("observaciones"));
                    p.setCodigoBarras(cb);

                    productos.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos por nombre", e);
        }
        return productos;
    }
}
