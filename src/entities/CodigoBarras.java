package entities;
import java.time.LocalDate;

public class CodigoBarras {
    private Long id;
    private Boolean eliminado;
    private TipoCodigo tipo;
    private String valor;
    private LocalDate fechaAsignacion;
    private String observaciones;


    public CodigoBarras() {} // constructor vacio

    // constructor completo
    public CodigoBarras(Long id, Boolean eliminado, TipoCodigo tipo, String valor,
                        LocalDate fechaAsignacion, String observaciones) {
        this.id = id;
        this.eliminado = eliminado;
        this.tipo = tipo;
        this.valor = valor;
        this.fechaAsignacion = fechaAsignacion;
        this.observaciones = observaciones;
    }

    // getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getEliminado() {
        return eliminado;
    }
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
    public TipoCodigo getTipo() {
        return tipo;
    }
    public void setTipo(TipoCodigo tipo) {
        this.tipo = tipo;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }
    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    // to string
    @Override
    public String toString() {
        return "CodigoBarras{" +
            "id=" + id +
            ", tipo=" + tipo +
            ", valor='" + valor + '\'' +
            ", fechaAsignacion=" + fechaAsignacion +
            '}';
}



}
