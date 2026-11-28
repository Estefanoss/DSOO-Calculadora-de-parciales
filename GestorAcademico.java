import java.io.Serializable;
import java.util.ArrayList;

public class GestorAcademico implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Curso> listaCursos;

    public GestorAcademico() {
        this.listaCursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {
        if (curso != null) {
            this.listaCursos.add(curso);
        }
    }

    public Curso buscarCurso(String nombre) {
        if (nombre == null) return null;
        for (Curso c : listaCursos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public double calcularPromedioPonderadoSemestral() {
        double sumaCreditos = 0.0;
        double sumaProductos = 0.0;
        for (Curso c : listaCursos) {
            if (c.getCreditos() > 0) {
                sumaProductos += c.obtenerNotaFinalRedondeada() * c.getCreditos();
                sumaCreditos += c.getCreditos();
            }
        }
        return (sumaCreditos == 0.0) ? 0.0 : (sumaProductos / sumaCreditos);
    }

    public ArrayList<Curso> getListaCursos() {
        return listaCursos;
    }
}