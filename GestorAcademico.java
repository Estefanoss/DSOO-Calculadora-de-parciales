import java.util.ArrayList;

public class GestorAcademico {
    private ArrayList<Curso> listaCursos;

    public GestorAcademico() {
        this.listaCursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {
        this.listaCursos.add(curso);
    }

    public Curso buscarCurso(String nombre) {
        for (Curso c : listaCursos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public void calcularEstadoGeneralSemestre() {
        
    }

    public ArrayList<Curso> getListaCursos() { return listaCursos; }
}