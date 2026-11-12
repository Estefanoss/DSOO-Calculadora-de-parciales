public class Fase {
    private int numeroFase;
    private double porcentajePonderado;
    private NotaContinua continua;
    private ExamenParcial examen;
    private boolean cerrada;

    public Fase(int numeroFase, double porcentajePonderado, NotaContinua continua, ExamenParcial examen) {
        this.numeroFase = numeroFase;
        this.porcentajePonderado = porcentajePonderado;
        this.continua = continua;
        this.examen = examen;
        this.cerrada = false;
    }

    public double calcularPromedioFase() {
        return continua.calcularAporteFase() + examen.calcularAporteFase();
    }

    public double calcularAporteFinal() {
        return calcularPromedioFase() * porcentajePonderado;
    }

    public int getNumeroFase() { return numeroFase; }
    public double getPorcentajePonderado() { return porcentajePonderado; }
    public void setPorcentajePonderado(double porcentajePonderado) { this.porcentajePonderado = porcentajePonderado; }
    public NotaContinua getContinua() { return continua; }
    public void setContinua(NotaContinua continua) { this.continua = continua; }
    public ExamenParcial getExamen() { return examen; }
    public void setExamen(ExamenParcial examen) { this.examen = examen; }
    public boolean isCerrada() { return cerrada; }
    public void setCerrada(boolean cerrada) { this.cerrada = cerrada; }
}