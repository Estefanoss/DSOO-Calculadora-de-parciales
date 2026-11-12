public abstract class Evaluacion {
    protected double peso;
    protected double nota;
    protected boolean esReemplazable;

    public Evaluacion(double peso, double nota, boolean esReemplazable) {
        this.peso = peso;
        this.nota = nota;
        this.esReemplazable = esReemplazable;
    }

    public abstract double calcularAporteFase();
    public abstract boolean aplicarSustitutorio(double nuevaNota);

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public boolean isEsReemplazable() { return esReemplazable; }
    public void setEsReemplazable(boolean esReemplazable) { this.esReemplazable = esReemplazable; }
}