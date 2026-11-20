public class Fase {
    private int numeroFase; // 1, 2 o 3
    private NotaContinua continua;
    private ExamenParcial examen;
    private boolean cerrada;

    public Fase(int numeroFase, NotaContinua continua, ExamenParcial examen) {
        this.numeroFase = numeroFase;
        this.continua = continua;
        this.examen = examen;
        this.cerrada = true;
    }

    // Suma directa del aporte ponderado de Continua + Examen de la fase
    public double calcularPuntajeFase() {
        double aporteContinua = (continua != null) ? continua.calcularAporte() : 0.0;
        double aporteExamen = (examen != null) ? examen.calcularAporte() : 0.0;
        return aporteContinua + aporteExamen;
    }

    public double calcularPesoFase() {
        double pesoContinua = (continua != null) ? continua.getPeso() : 0.0;
        double pesoExamen = (examen != null) ? examen.getPeso() : 0.0;
        return pesoContinua + pesoExamen;
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