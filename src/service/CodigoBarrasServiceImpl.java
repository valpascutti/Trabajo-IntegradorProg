package service;

import dao.CodigoBarrasDAO;
import dao.CodigoBarrasDAOImpl;
import entities.CodigoBarras;
import java.util.List;

public class CodigoBarrasServiceImpl implements CodigoBarrasService {

    private final CodigoBarrasDAO codigoDAO;

    public CodigoBarrasServiceImpl() {
        this.codigoDAO = new CodigoBarrasDAOImpl();
    }

    @Override
    public void crearCodigoBarras(CodigoBarras codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("El código de barras no puede ser null");
        }
        if (codigo.getValor() == null || codigo.getValor().isEmpty()) {
            throw new IllegalArgumentException("El valor del código no puede estar vacío");
        }
        codigo.setEliminado(false);
        codigoDAO.insertar(codigo);
    }

    @Override
    public CodigoBarras obtenerPorId(Long id) {
        return codigoDAO.obtenerPorId(id);
    }

    @Override
    public CodigoBarras buscarPorNumero(String numero) {
        return codigoDAO.buscarPorNumero(numero);
    }

    @Override
    public List<CodigoBarras> listarTodos() {
        return codigoDAO.listarTodos();
    }

    @Override
    public void actualizar(CodigoBarras codigo) {
        if (codigo == null || codigo.getId() == null) {
            throw new IllegalArgumentException("El código a actualizar no es válido");
        }
        codigoDAO.actualizar(codigo);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }
        codigoDAO.eliminar(id);
    }
}
