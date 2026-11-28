import java.util.ArrayList;

public class NotaContinua extends Evaluacion {
    private static final long serialVersionUID = 1L;
    private ArrayList<Double> subNotas;

    public NotaContinua(String etiqueta, double peso, double nota) {
        super(etiqueta, peso, nota, false);
        this.subNotas = new ArrayList<>();
    }

    public void agregarSubNota(double subNota) {
        if (subNota >= ReglamentoUNSA.NOTA_MINIMA && subNota <= ReglamentoUNSA.NOTA_MAXIMA) {
            subNotas.add(subNota);
            recalcularNotaDesdeSubNotas();
        }
    }

    public void recalcularNotaDesdeSubNotas() {
        if (!subNotas.isEmpty()) {
            double suma = 0.0;
            for (double n : subNotas) {
                suma += n;
            }
            this.nota = suma / subNotas.size();
        }
    }

    public ArrayList<Double> getSubNotas() {
        return subNotas;
    }

    @Override
    public double calcularAporte() {
        return this.nota * this.peso;
    }

    @Override
    public boolean aplicarSustitutorio(double nuevaNota) {
        return false; // La nota continua no se sustituye
    }
}