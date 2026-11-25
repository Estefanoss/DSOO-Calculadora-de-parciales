import java.io.Serializable;
import java.util.ArrayList;
public class GestorAcademico implements Serializable{
    private final ArrayList<Curso> listaCursos = new ArrayList<>();

    public GestorAcademico() {}
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
    public void eliminarCurso(String nombre){
        int pos=0;
        for(int i=0;i<listaCursos.size();i++){
            if(listaCursos.get(i).getNombre().equals(nombre)){
                pos = i;
            }
        }
        listaCursos.remove(pos);
    }
    public ArrayList<Curso> getListaCursos() { return listaCursos; }
}