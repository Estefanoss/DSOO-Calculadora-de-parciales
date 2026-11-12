import java.util.ArrayList;

public class Curso {
    private String nombre;
    private double pesoContinua;
    private double pesoExamen;
    private ArrayList<Fase> fases;
    private ExamenSustitutorio sustitutorio;
    private double notaAprobatoriaMinima;

    public Curso(String nombre, double pesoContinua, double pesoExamen) {
        this.nombre = nombre;
        this.pesoContinua = pesoContinua;
        this.pesoExamen = pesoExamen;
        this.fases = new ArrayList<>();
        this.notaAprobatoriaMinima = ReglamentoUNSA.NOTA_APROBATORIA;
    }

    public void agregarFase(Fase fase) {
        this.fases.add(fase);
    }

    public double calcularPromedioPonderadoActual() {
        double suma = 0;
        for (Fase f : fases) {
            suma += f.calcularAporteFinal();
        }
        return suma;
    }

    public double calcProyeccionAprobar(double puntajeObtenido) {
        return Math.max(0, notaAprobatoriaMinima - puntajeObtenido);
    }

    public String analizarConvenienciaSustitutorio() {
        return "Análisis de conveniencia basado en las notas de las fases registradas.";
    }

    public boolean registrarSustitutorio(double nota) {
        this.sustitutorio = new ExamenSustitutorio(nota);
        if (fases.size() == 3) {
            ExamenParcial ep1 = fases.get(0).getExamen();
            ExamenParcial ep2 = fases.get(1).getExamen();
            ExamenParcial ep3 = fases.get(2).getExamen();
            ExamenParcial optimo = this.sustitutorio.evaluarReemplazoOptimo(ep1, ep2, ep3);
            return this.sustitutorio.aplicarReemplazo(optimo);
        }
        return false;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public ArrayList<Fase> getListaFases() { return fases; }
    public ExamenSustitutorio getSustitutorio() { return sustitutorio; }
}