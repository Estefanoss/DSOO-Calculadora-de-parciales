public class ExamenParcial extends Evaluacion {
    private int numeroParcial;
    private double notaOriginal;

    public ExamenParcial(int numeroParcial, double peso, double nota) {
        super(peso, nota, true);
        this.numeroParcial = numeroParcial;
        this.notaOriginal = nota;
    }

    @Override
    public double calcularAporteFase() {
        return nota * peso;
    }

    @Override
    public boolean aplicarSustitutorio(double nuevaNota) {
        if (esReemplazable) {
            this.nota = nuevaNota;
            return true;
        }
        return false;
    }

    public int getNumeroParcial() { return numeroParcial; }
    public double getNotaOriginal() { return notaOriginal; }
}