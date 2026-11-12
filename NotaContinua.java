public class NotaContinua extends Evaluacion {

    public NotaContinua(double peso, double nota) {
        super(peso, nota, false); 
    }

    @Override
    public double calcularAporteFase() {
        return nota * peso;
    }

    @Override
    public boolean aplicarSustitutorio(double nuevaNota) {
        return false; // Por lo general, la nota continua no es reemplazable
    }
}