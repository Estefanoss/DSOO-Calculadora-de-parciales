
import java.util.ArrayList;

public class ExamenSustitutorio {
    private double nota;
    private ExamenParcial examenReemplazado;
    private boolean aplicado;

    public ExamenSustitutorio(double nota) {
        this.nota = nota;
        this.aplicado = false;
    }

    public static boolean esElegible(ArrayList<Fase> fases) {
        boolean valor = fases.get(0).isCerrada();
        for(int i=1;i<fases.size();i++){
                valor =  valor && fases.get(i).isCerrada();
        }
        return valor;
    }

    public ExamenParcial evaluarReemplazoOptimo(ExamenParcial parcial1, ExamenParcial parcial2) {
        ExamenParcial menor = parcial1;
        if (parcial2 != null && parcial2.getNota() < menor.getNota()) menor = parcial2;
        return menor;
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