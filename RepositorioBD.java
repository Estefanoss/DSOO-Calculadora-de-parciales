import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioBD {
    private static final String URL = "jdbc:sqlite:datos/unsa_academico.db";

    public static void inicializar() {
        String sqlCursos = "CREATE TABLE IF NOT EXISTS cursos (nombre TEXT PRIMARY KEY, creditos INTEGER NOT NULL);";
        String sqlFases = """
                CREATE TABLE IF NOT EXISTS fases (
                    curso_nombre TEXT NOT NULL,
                    numero_fase INTEGER NOT NULL,
                    peso_continua REAL NOT NULL,
                    nota_continua REAL NOT NULL,
                    peso_examen REAL NOT NULL,
                    nota_examen REAL NOT NULL,
                    cerrada INTEGER NOT NULL,
                    PRIMARY KEY (curso_nombre, numero_fase)
                );
                """;
        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {
            st.execute(sqlCursos);
            st.execute(sqlFases);
        } catch (SQLException ignored) {
            // Si falta sqlite-jdbc.jar en lib/, la app sigue funcionando con archivos
        }
    }

    public static void guardarCurso(Curso curso) {
        String sql = "INSERT OR REPLACE INTO cursos(nombre, creditos) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getCreditos());
            ps.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public static void guardarFase(String nombreCurso, Fase fase) {
        String sql = """
                INSERT OR REPLACE INTO fases(
                    curso_nombre, numero_fase, peso_continua, nota_continua,
                    peso_examen, nota_examen, cerrada
                ) VALUES(?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreCurso);
            ps.setInt(2, fase.getNumeroFase());
            ps.setDouble(3, fase.getContinua().getPeso());
            ps.setDouble(4, fase.getContinua().getNota());
            ps.setDouble(5, fase.getExamen().getPeso());
            ps.setDouble(6, fase.getExamen().getNota());
            ps.setInt(7, fase.isCerrada() ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public static List<Curso> cargarTodo() {
        List<Curso> cursos = new ArrayList<>();
        String sqlC = "SELECT nombre, creditos FROM cursos";
        String sqlF = """
                SELECT numero_fase, peso_continua, nota_continua, peso_examen, nota_examen, cerrada
                FROM fases WHERE curso_nombre = ? ORDER BY numero_fase ASC
                """;
        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement();
             ResultSet rsC = st.executeQuery(sqlC)) {
            while (rsC.next()) {
                Curso c = new Curso(rsC.getString("nombre"), rsC.getInt("creditos"));
                try (PreparedStatement psF = conn.prepareStatement(sqlF)) {
                    psF.setString(1, c.getNombre());
                    try (ResultSet rsF = psF.executeQuery()) {
                        while (rsF.next()) {
                            int num = rsF.getInt("numero_fase");
                            NotaContinua nc = new NotaContinua("Continua " + num, rsF.getDouble("peso_continua"), rsF.getDouble("nota_continua"));
                            ExamenParcial ep = new ExamenParcial(num, "Examen " + num, rsF.getDouble("peso_examen"), rsF.getDouble("nota_examen"));
                            Fase f = new Fase(num, nc, ep);
                            f.setCerrada(rsF.getInt("cerrada") == 1);
                            c.agregarOActualizarFase(f);
                        }
                    }
                }
                cursos.add(c);
            }
        } catch (SQLException ignored) {
        }
        return cursos;
    }
}