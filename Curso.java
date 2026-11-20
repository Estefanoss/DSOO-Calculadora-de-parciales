import java.util.ArrayList;

public class Curso {
    private String nombre;
    private int creditos;
    private ArrayList<Fase> fases;
    private ExamenSustitutorio sustitutorio;
    private double notaAprobatoriaMinima;

    public Curso(String nombre, int creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.fases = new ArrayList<>();
        this.notaAprobatoriaMinima = ReglamentoUNSA.NOTA_APROBATORIA;
    }

    public boolean agregarOActualizarFase(Fase nuevaFase) {
        if (nuevaFase == null || nuevaFase.getNumeroFase() < 1 || nuevaFase.getNumeroFase() > ReglamentoUNSA.TOTAL_FASES) {
            return false;
        }
        for (int i = 0; i < fases.size(); i++) {
            if (fases.get(i).getNumeroFase() == nuevaFase.getNumeroFase()) {
                fases.set(i, nuevaFase);
                return true;
            }
        }
        fases.add(nuevaFase);
        return true;
    }

    public Fase buscarFase(int numeroFase) {
        for (Fase f : fases) {
            if (f.getNumeroFase() == numeroFase) {
                return f;
            }
        }
        return null;
    }

    public double calcularPromedioPonderadoActual() {
        double suma = 0.0;
        for (Fase f : fases) {
            suma += f.calcularPuntajeFase();
        }
        return suma;
    }

    public double calcularPesoRegistrado() {
        double pesoTotal = 0.0;
        for (Fase f : fases) {
            pesoTotal += f.calcularPesoFase();
        }
        return pesoTotal;
    }

    public double calcularPesoRestante() {
        return Math.max(0.0, 1.0 - calcularPesoRegistrado());
    }

    public double calcularPuntosFaltantes() {
        return Math.max(0.0, notaAprobatoriaMinima - calcularPromedioPonderadoActual());
    }

    public boolean puedeRendirSustitutorio() {
        Fase f1 = buscarFase(1);
        Fase f2 = buscarFase(2);
        Fase f3 = buscarFase(3);
        boolean f1Ok = (f1 != null && f1.isCerrada());
        boolean f2Ok = (f2 != null && f2.isCerrada());
        boolean f3NoDada = (f3 == null);
        ExamenSustitutorio temp = new ExamenSustitutorio(0);
        return temp.esElegible(f1Ok, f2Ok, f3NoDada);
    }

    public ExamenParcial obtenerExamenObjetivoSustitutorio() {
        if (!puedeRendirSustitutorio()) {
            return null;
        }
        Fase f1 = buscarFase(1);
        Fase f2 = buscarFase(2);
        ExamenSustitutorio temp = new ExamenSustitutorio(0);
        return temp.seleccionarExamenMinimoFase1o2(f1.getExamen(), f2.getExamen());
    }

    public boolean registrarSustitutorio(double notaSustitutorio) {
        if (!puedeRendirSustitutorio()) {
            return false;
        }
        Fase f1 = buscarFase(1);
        Fase f2 = buscarFase(2);
        this.sustitutorio = new ExamenSustitutorio(notaSustitutorio);
        return this.sustitutorio.aplicarReemplazoDirecto(f1.getExamen(), f2.getExamen());
    }

    public int obtenerNotaFinalRedondeada() {
        return (int) Math.round(calcularPromedioPonderadoActual());
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public ArrayList<Fase> getListaFases() { return fases; }
    public ExamenSustitutorio getSustitutorio() { return sustitutorio; }
    public double getNotaAprobatoriaMinima() { return notaAprobatoriaMinima; }
}