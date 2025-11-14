package dao;

import config.ConexionDB;
import entities.CodigoBarras;
import entities.TipoCodigo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodigoBarrasDAOImpl implements CodigoBarrasDAO {

    @Override
    public void insertar(CodigoBarras cb) {
        String sql = "INSERT INTO codigo_barras (valor, tipo, observaciones, eliminado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cb.getValor());
            ps.setString(2, cb.getTipo().name());
            ps.setString(3, cb.getObservaciones());
            ps.setBoolean(4, cb.getEliminado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cb.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar código de barras", e);
        }
    }

    @Override
    public CodigoBarras obtenerPorId(Long id) {
        String sql = "SELECT * FROM codigo_barras WHERE id = ? AND eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCodigoBarras(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener código de barras", e);
        }
        return null;
    }

    @Override
    public List<CodigoBarras> listarTodos() {
        List<CodigoBarras> codigos = new ArrayList<>();
        String sql = "SELECT * FROM codigo_barras WHERE eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                codigos.add(mapearCodigoBarras(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar códigos de barras", e);
        }
        return codigos;
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE codigo_barras SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar código de barras", e);
        }
    }

    @Override
    public void actualizar(CodigoBarras cb) {
        String sql = "UPDATE codigo_barras SET valor=?, tipo=?, observaciones=? WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cb.getValor());
            ps.setString(2, cb.getTipo().name());
            ps.setString(3, cb.getObservaciones());
            ps.setLong(4, cb.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar código de barras", e);
        }
    }

    private CodigoBarras mapearCodigoBarras(ResultSet rs) throws SQLException {
        CodigoBarras cb = new CodigoBarras();
        cb.setId(rs.getLong("id"));
        cb.setValor(rs.getString("valor"));
        cb.setTipo(TipoCodigo.valueOf(rs.getString("tipo")));
        cb.setObservaciones(rs.getString("observaciones"));
        cb.setEliminado(rs.getBoolean("eliminado"));
        return cb;
    }

    @Override
    public CodigoBarras buscarPorNumero(String numero) {
        String sql = "SELECT * FROM codigo_barras WHERE valor = ? AND eliminado = false";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCodigoBarras(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar código de barras por número", e);
        }
        return null;
    }
}
