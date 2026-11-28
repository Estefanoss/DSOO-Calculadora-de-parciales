import java.io.Serializable;

public class Fase implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numeroFase;
    private NotaContinua continua;
    private ExamenParcial examen;
    private boolean cerrada;

    public Fase(int numeroFase, NotaContinua continua, ExamenParcial examen) {
        this.numeroFase = numeroFase;
        this.continua = continua;
        this.examen = examen;
        this.cerrada = true;
    }

    // Suma directa sin doble ponderación
    public double calcularPuntajeFase() {
        double aporteC = (continua != null) ? continua.calcularAporte() : 0.0;
        double aporteE = (examen != null) ? examen.calcularAporte() : 0.0;
        return aporteC + aporteE;
    }

    public double calcularPesoFase() {
        double pesoC = (continua != null) ? continua.getPeso() : 0.0;
        double pesoE = (examen != null) ? examen.getPeso() : 0.0;
        return pesoC + pesoE;
    }

    public int getNumeroFase() { return numeroFase; }
    public void setNumeroFase(int numeroFase) { this.numeroFase = numeroFase; }

    public NotaContinua getContinua() { return continua; }
    public void setContinua(NotaContinua continua) { this.continua = continua; }

    public ExamenParcial getExamen() { return examen; }
    public void setExamen(ExamenParcial examen) { this.examen = examen; }

    public boolean isCerrada() { return cerrada; }
    public void setCerrada(boolean cerrada) { this.cerrada = cerrada; }
}