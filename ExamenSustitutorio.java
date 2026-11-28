import java.io.Serializable;

public class ExamenSustitutorio implements Serializable {
    private static final long serialVersionUID = 1L;
    private double nota;
    private ExamenParcial examenReemplazado;
    private boolean aplicado;

    public ExamenSustitutorio(double nota) {
        this.nota = nota;
        this.aplicado = false;
    }

    // Solo procede tras cerrar Fase 1 y Fase 2, y antes de registrar Fase 3
    public boolean esElegible(boolean fase1Cerrada, boolean fase2Cerrada, boolean fase3Registrada) {
        return fase1Cerrada && fase2Cerrada && !fase3Registrada;
    }

    // Selecciona el examen de menor nota únicamente entre Parcial 1 y Parcial 2
    public ExamenParcial seleccionarExamenMinimoFase1o2(ExamenParcial parcial1, ExamenParcial parcial2) {
        if (parcial1 == null && parcial2 == null) {
            return null;
        }
        if (parcial1 == null) {
            return parcial2;
        }
        if (parcial2 == null) {
            return parcial1;
        }
        return (parcial2.getNota() < parcial1.getNota()) ? parcial2 : parcial1;
    }

    public boolean aplicarReemplazoDirecto(ExamenParcial parcial1, ExamenParcial parcial2) {
        ExamenParcial objetivo = seleccionarExamenMinimoFase1o2(parcial1, parcial2);
        if (objetivo != null && objetivo.aplicarSustitutorio(this.nota)) {
            this.examenReemplazado = objetivo;
            this.aplicado = true;
            return true;
        }
        return false;
    }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public ExamenParcial getExamenReemplazado() { return examenReemplazado; }
    public boolean isAplicado() { return aplicado; }
}