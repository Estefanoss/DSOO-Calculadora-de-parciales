import java.io.Serializable;
public class NotaContinua extends Evaluacion implements Serializable {
    public NotaContinua(double peso, double nota) {
        super(peso, nota, false); 
    }
    @Override
    public double calcularAporteFase() {
        if(nota==-1)
            return 0;
        return nota * peso;
    }

    @Override
    public boolean aplicarSustitutorio(double nuevaNota) {
        return false;
    }
}