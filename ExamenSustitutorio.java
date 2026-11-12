public class ExamenSustitutorio {
    private double nota;
    private ExamenParcial examenReemplazado;
    private boolean aplicado;

    public ExamenSustitutorio(double nota) {
        this.nota = nota;
        this.aplicado = false;
    }

    public boolean esElegible(boolean fase1Cerrada, boolean fase2Cerrada, boolean fase3Cerrada) {
        return fase1Cerrada && fase2Cerrada && fase3Cerrada;
    }

    public ExamenParcial evaluarReemplazoOptimo(ExamenParcial parcial1, ExamenParcial parcial2, ExamenParcial parcial3) {
        ExamenParcial menor = parcial1;
        if (parcial2 != null && parcial2.getNota() < menor.getNota()) menor = parcial2;
        if (parcial3 != null && parcial3.getNota() < menor.getNota()) menor = parcial3;
        
        if (this.nota > menor.getNota()) {
            return menor;
        }
        return null;
    }

    public boolean aplicarReemplazo(ExamenParcial parcial) {
        if (parcial != null && this.nota > parcial.getNota()) {
            parcial.aplicarSustitutorio(this.nota);
            this.examenReemplazado = parcial;
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