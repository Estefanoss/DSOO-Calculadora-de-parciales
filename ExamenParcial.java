public class ExamenParcial extends Evaluacion {
    private int numeroParcial; // 1, 2 o 3
    private double notaOriginal;
    private boolean fueSustituido;

    public ExamenParcial(int numeroParcial, String etiqueta, double peso, double nota) {
        // Solo los parciales 1 y 2 pueden ser sustituidos antes del 3er parcial
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
        // Reemplazo incondicional: reemplaza la nota mínima elegida sea mayor o menor
        this.nota = nuevaNota;
        this.fueSustituido = true;
        return true;
    }

    public int getNumeroParcial() { return numeroParcial; }
    public double getNotaOriginal() { return notaOriginal; }
    public boolean isFueSustituido() { return fueSustituido; }
}