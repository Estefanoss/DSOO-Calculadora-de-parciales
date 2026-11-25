import java.io.*;
import java.nio.file.*;

public class UserRepository {
    private final Path rutaArchivo;
    public UserRepository() throws IOException {
        this.rutaArchivo = Paths.get("data");
    }
    public void guardarGestor(String nombreArchivo, GestorAcademico gestor) throws IOException {
        Path rutaArchivo = resolverRuta(nombreArchivo);
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(rutaArchivo))) {
            oos.writeObject(gestor);
        }
    }
    private Path resolverRuta(String nombreArchivo) {
        String nombreFinal = nombreArchivo.endsWith(".dat") ? nombreArchivo : nombreArchivo + ".dat";
        return rutaArchivo.resolve(nombreFinal);
    }
}
