public class Fase {
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
    public double calcularAporteTotal() {
        return continua.calcularAporteFase() + examen.calcularAporteFase();
    }
    public int getNumeroFase() { return numeroFase; }
    public NotaContinua getContinua() { return continua; }
    public void setContinua(NotaContinua continua) { this.continua = continua; }
    public ExamenParcial getExamen() { return examen; }
    public void setExamen(ExamenParcial examen) { this.examen = examen; }
    public boolean isCerrada() { return cerrada; }
    public void setCerrada(boolean cerrada) { this.cerrada = cerrada; }
}