package dao;

import entities.CodigoBarras;

public interface CodigoBarrasDAO extends GenericDAO<CodigoBarras> {
    CodigoBarras buscarPorNumero(String numero);
}

