package service;

import entities.CodigoBarras;
import java.util.List;

public interface CodigoBarrasService {

    void crearCodigoBarras(CodigoBarras codigo);

    CodigoBarras obtenerPorId(Long id);

    CodigoBarras buscarPorNumero(String numero);

    List<CodigoBarras> listarTodos();

    void actualizar(CodigoBarras codigo);

    void eliminar(Long id);
}
