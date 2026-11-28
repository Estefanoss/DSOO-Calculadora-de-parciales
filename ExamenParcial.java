public class ExamenParcial extends Evaluacion {
    private static final long serialVersionUID = 1L;
    private int numeroParcial;
    private double notaOriginal;
    private boolean fueSustituido;

    public ExamenParcial(int numeroParcial, String etiqueta, double peso, double nota) {
        super(etiqueta, peso, nota, numeroParcial == 1 || numeroParcial == 2);
        this.numeroParcial = numeroParcial;
        this.notaOriginal = nota;
        this.fueSustituido = false;
    }

    @Override
    public double calcularAporte() {
        return this.nota * this.peso;
    }

    @Override
    public boolean aplicarSustitutorio(double nuevaNota) {
        if (!esReemplazable) {
            return false;
        }
        // Reemplazo incondicional sobre el examen elegido (suba o baje la nota)
        this.nota = nuevaNota;
        this.fueSustituido = true;
        return true;
    }

    public int getNumeroParcial() { return numeroParcial; }
    public double getNotaOriginal() { return notaOriginal; }
    public boolean isFueSustituido() { return fueSustituido; }
}