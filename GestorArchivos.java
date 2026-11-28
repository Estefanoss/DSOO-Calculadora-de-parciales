import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorArchivos {

    // 1. ARCHIVOS DE TEXTO (PrintWriter + FileWriter / Scanner + FileReader)
    public static void guardarTexto(File archivo, GestorAcademico gestor, Curso cursoActual) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(archivo, StandardCharsets.UTF_8));
        out.println("=== REPORTE ACADEMICO UNSA ===");
        out.printf("PPS: %.2f%n", gestor.calcularPromedioPonderadoSemestral());
        if (cursoActual != null) {
            out.print(ConstruirTextoAnalisis.generar(cursoActual, gestor));
        } else {
            for (Curso c : gestor.getListaCursos()) {
                out.print(ConstruirTextoAnalisis.generar(c, gestor));
                out.println("----------------------------------------");
            }
        }
        out.close();
    }

    public static String leerTexto(File archivo) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(archivo));
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            sb.append(in.nextLine()).append("\n");
        }
        in.close();
        return sb.toString();
    }

    // 2. ARCHIVOS BINARIOS (DataOutputStream + FileOutputStream / DataInputStream + FileInputStream)
    public static void guardarBinario(File archivo, GestorAcademico gestor) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(archivo));
        ArrayList<Curso> cursos = gestor.getListaCursos();
        out.writeInt(cursos.size());
        for (Curso c : cursos) {
            out.writeUTF(c.getNombre());
            out.writeInt(c.getCreditos());
            out.writeDouble(c.calcularPromedioPonderadoActual());
        }
        out.close();
    }

    public static String leerBinario(File archivo) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(archivo));
        int cantidad = in.readInt();
        StringBuilder sb = new StringBuilder("=== ARCHIVO BINARIO (" + cantidad + " cursos) ===\n");
        for (int i = 0; i < cantidad; i++) {
            String nombre = in.readUTF();
            int cred = in.readInt();
            double acum = in.readDouble();
            sb.append(String.format("- %s | Cred: %d | Acumulado: %.2f%n", nombre, cred, acum));
        }
        in.close();
        return sb.toString();
    }

    // 3. ARCHIVOS DE OBJETOS (ObjectOutputStream / ObjectInputStream con Serializable)
    public static void guardarObjetos(File archivo, ArrayList<Curso> cursos) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo));
        out.writeObject(cursos);
        out.close();
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Curso> leerObjetos(File archivo) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo));
        ArrayList<Curso> cursos = (ArrayList<Curso>) in.readObject();
        in.close();
        return cursos;
    }
}