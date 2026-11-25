import java.io.Serializable;
import java.util.ArrayList;
public class Curso implements Serializable{
    private String nombre;
    private final ArrayList<Fase> fases = new ArrayList<>();
    private ExamenSustitutorio sustitutorio;
    private final double notaAprobatoriaMinima = ReglamentoUNSA.NOTA_APROBATORIA;

    public Curso(String nombre) {
        this.nombre = nombre;
    }

    public void agregarFase(Fase fase) {
        this.fases.add(fase);
    }

    public double calcularPromedioPonderadoActual() {
        double suma = 0;
        for (Fase f : fases) {
            suma += f.calcularAporteTotal();
        }
        return suma;
    }

    public double calcProyeccionAprobar(double puntajeObtenido) {
        return Math.max(0, notaAprobatoriaMinima - puntajeObtenido);
    }
    public boolean registrarSustitutorio(double nota) {
        this.sustitutorio = new ExamenSustitutorio(nota);
        if (fases.size() == 3) {
            ExamenParcial ep1 = fases.get(0).getExamen();
            ExamenParcial ep2 = fases.get(1).getExamen();
            ExamenParcial optimo = this.sustitutorio.evaluarReemplazoOptimo(ep1, ep2);
            return this.sustitutorio.aplicarReemplazo(optimo);
        }
        return false;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public ArrayList<Fase> getListaFases() { return fases; }
    public ExamenSustitutorio getSustitutorio() { return sustitutorio; }
}