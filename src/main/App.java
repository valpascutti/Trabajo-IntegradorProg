package main;

import java.time.LocalDate;

import entities.CodigoBarras;
import entities.Producto;
import entities.TipoCodigo;

public class App {
    public static void main(String[] args) {
        // solo para probr que funcionen las clases

        CodigoBarras cb = new CodigoBarras(
                1L, false, TipoCodigo.EAN13, "1234567890123",
                LocalDate.now(), "Primera asignación"
        );

        Producto p = new Producto(
                1L, false, "Gaseosa", "Coc", "Bebidas",
                3000.0, 0.15, cb
        );

        System.out.println("Producto: " + p.getNombre());
        System.out.println("Código de barras: " + p.getCodigoBarras().getValor());
    }
}

