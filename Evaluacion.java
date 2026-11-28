import java.io.Serializable;

public abstract class Evaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String etiqueta;
    protected double peso; // Peso porcentual directo sobre el curso (ej: 0.20 = 20%)
    protected double nota;
    protected boolean esReemplazable;

    public Evaluacion(String etiqueta, double peso, double nota, boolean esReemplazable) {
        this.etiqueta = etiqueta;
        this.peso = peso;
        this.nota = nota;
        this.esReemplazable = esReemplazable;
    }

    public abstract double calcularAporte();
    public abstract boolean aplicarSustitutorio(double nuevaNota);

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public boolean isEsReemplazable() { return esReemplazable; }
    public void setEsReemplazable(boolean esReemplazable) { this.esReemplazable = esReemplazable; }
}