import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GestorAcademicoGUI extends JFrame {

    private static final Color COLOR_ROJO_UNSA = new Color(138, 21, 36);
    private static final Color COLOR_GUINDA_OSCURO = new Color(92, 12, 22);
    private static final Color COLOR_DORADO = new Color(212, 160, 23);
    private static final Color COLOR_FONDO = new Color(245, 246, 248);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_TEXTO = new Color(33, 37, 41);

    private GestorAcademico gestor;
    private JTabbedPane tabbedPane;

    // Componentes pestaña Cursos
    private JTextField txtNombreCurso, txtCreditosCurso;
    private DefaultListModel<String> modeloListaCursos;

    // Componentes pestaña Fases y Pesos
    private JComboBox<String> cbCursosFase;
    private JComboBox<Integer> cbNumeroFase;
    private JTextField txtPesoContinua, txtNotaContinua;
    private JTextField txtPesoExamen, txtNotaExamen;

    // Componentes pestaña Sustitutorio y Análisis
    private JComboBox<String> cbCursosAnalisis;
    private JTextField txtNotaSustitutorio;
    private JTextArea txtAreaReporte;

    public GestorAcademicoGUI() {
        this.gestor = new GestorAcademico();
        configurarVentana();
        construirInterfaz();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("UNSA - Simulador Estratégico Académico");
        setSize(820, 620);
        setMinimumSize(new Dimension(720, 540));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz() {
        // Encabezado UNSA
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_ROJO_UNSA);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel lblUniversidad = new JLabel("UNIVERSIDAD NACIONAL DE SAN AGUSTÍN — UNSA");
        lblUniversidad.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblUniversidad.setForeground(COLOR_BLANCO);

        JLabel lblSubtitulo = new JLabel("Simulador de Fases, Pesos Variables y Sustitutorio (Regla Pre-3er Parcial)");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(255, 235, 205));

        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 2));
        titulos.setOpaque(false);
        titulos.add(lblUniversidad);
        titulos.add(lblSubtitulo);

        JButton btnSalir = crearBoton("Salir", COLOR_GUINDA_OSCURO, COLOR_BLANCO);
        btnSalir.addActionListener(e -> System.exit(0));

        header.add(titulos, BorderLayout.WEST);
        header.add(btnSalir, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Pestañas principales
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBorder(new EmptyBorder(10, 12, 12, 12));

        tabbedPane.addTab("1. Cursos", crearPanelCursos());
        tabbedPane.addTab("2. Fases y Pesos", crearPanelFases());
        tabbedPane.addTab("3. Sustitutorio y Proyección", crearPanelAnalisis());

        tabbedPane.addChangeListener(e -> refrescarCombosYLista());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(COLOR_BLANCO);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 228)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        txtNombreCurso = new JTextField();
        txtCreditosCurso = new JTextField();

        form.add(crearEtiqueta("Nombre del Curso (ej. Estructuras Discretas II):"));
        form.add(txtNombreCurso);
        form.add(crearEtiqueta("Créditos del Curso (ej. 4):"));
        form.add(txtCreditosCurso);

        JButton btnAgregarCurso = crearBoton("Registrar Curso", COLOR_ROJO_UNSA, COLOR_BLANCO);
        btnAgregarCurso.addActionListener(e -> registrarCurso());

        JPanel topBox = new JPanel(new BorderLayout(0, 10));
        topBox.setOpaque(false);
        topBox.add(form, BorderLayout.CENTER);
        topBox.add(btnAgregarCurso, BorderLayout.SOUTH);

        modeloListaCursos = new DefaultListModel<>();
        JList<String> listaVisual = new JList<>(modeloListaCursos);
        listaVisual.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(listaVisual);
        scroll.setBorder(BorderFactory.createTitledBorder("Cursos Registrados en el Ciclo"));

        panel.add(topBox, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFases() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel grid = new JPanel(new GridLayout(6, 2, 10, 10));
        grid.setBackground(COLOR_BLANCO);
        grid.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 228)),
                new EmptyBorder(16, 16, 16, 16)
        ));

        cbCursosFase = new JComboBox<>();
        cbNumeroFase = new JComboBox<>(new Integer[]{1, 2, 3});
        txtPesoContinua = new JTextField("0.20");
        txtNotaContinua = new JTextField();
        txtPesoExamen = new JTextField("0.10");
        txtNotaExamen = new JTextField();

        grid.add(crearEtiqueta("Seleccionar Curso:"));
        grid.add(cbCursosFase);

        grid.add(crearEtiqueta("Fase / Parcial (1, 2 o 3):"));
        grid.add(cbNumeroFase);

        grid.add(crearEtiqueta("Peso Continua (decimal, ej. 0.20 = 20%):"));
        grid.add(txtPesoContinua);

        grid.add(crearEtiqueta("Nota Continua (0.0 - 20.0):"));
        grid.add(txtNotaContinua);

        grid.add(crearEtiqueta("Peso Examen Parcial (decimal, ej. 0.10 = 10%):"));
        grid.add(txtPesoExamen);

        grid.add(crearEtiqueta("Nota Examen Parcial (0.0 - 20.0):"));
        grid.add(txtNotaExamen);

        JButton btnGuardarFase = crearBoton("Guardar Fase y Pesos", COLOR_ROJO_UNSA, COLOR_BLANCO);
        btnGuardarFase.addActionListener(e -> guardarFase());

        JButton btnIrAnalisis = crearBoton("Ver Análisis y Sustitutorio →", COLOR_DORADO, COLOR_TEXTO);
        btnIrAnalisis.addActionListener(e -> {
            refrescarCombosYLista();
            tabbedPane.setSelectedIndex(2);
        });

        JPanel botones = new JPanel(new GridLayout(1, 2, 12, 0));
        botones.setOpaque(false);
        botones.add(btnGuardarFase);
        botones.add(btnIrAnalisis);

        panel.add(grid, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelAnalisis() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        barraSuperior.setBackground(COLOR_BLANCO);
        barraSuperior.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 228)));

        cbCursosAnalisis = new JComboBox<>();
        txtNotaSustitutorio = new JTextField(6);

        JButton btnCalcular = crearBoton("Calcular y Proyectar", COLOR_ROJO_UNSA, COLOR_BLANCO);
        btnCalcular.addActionListener(e -> mostrarAnalisis());

        JButton btnAplicarSusti = crearBoton("Aplicar Sustitutorio (Pre-Fase 3)", COLOR_DORADO, COLOR_TEXTO);
        btnAplicarSusti.addActionListener(e -> aplicarSustitutorioUI());

        barraSuperior.add(crearEtiqueta("Curso:"));
        barraSuperior.add(cbCursosAnalisis);
        barraSuperior.add(btnCalcular);
        barraSuperior.add(crearEtiqueta("Nota Susti:"));
        barraSuperior.add(txtNotaSustitutorio);
        barraSuperior.add(btnAplicarSusti);

        txtAreaReporte = new JTextArea();
        txtAreaReporte.setEditable(false);
        txtAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaReporte.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(txtAreaReporte);
        scroll.setBorder(BorderFactory.createTitledBorder("Reporte Académico UNSA"));

        panel.add(barraSuperior, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void registrarCurso() {
        String nombre = txtNombreCurso.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el nombre del curso.");
            return;
        }
        try {
            int creditos = Integer.parseInt(txtCreditosCurso.getText().trim());
            if (creditos <= 0) {
                JOptionPane.showMessageDialog(this, "Los créditos deben ser mayores a 0.");
                return;
            }
            gestor.agregarCurso(new Curso(nombre, creditos));
            txtNombreCurso.setText("");
            txtCreditosCurso.setText("");
            refrescarCombosYLista();
            JOptionPane.showMessageDialog(this, "Curso registrado correctamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número entero válido para los créditos.");
        }
    }

    private void guardarFase() {
        String nombreCurso = (String) cbCursosFase.getSelectedItem();
        Curso curso = gestor.buscarCurso(nombreCurso);
        if (curso == null) {
            JOptionPane.showMessageDialog(this, "Primero registra o selecciona un curso.");
            return;
        }

        try {
            int numFase = (Integer) cbNumeroFase.getSelectedItem();
            double pesoC = Double.parseDouble(txtPesoContinua.getText().trim());
            double notaC = Double.parseDouble(txtNotaContinua.getText().trim());
            double pesoE = Double.parseDouble(txtPesoExamen.getText().trim());
            double notaE = Double.parseDouble(txtNotaExamen.getText().trim());

            if (pesoC < 0 || pesoE < 0 || pesoC > 1 || pesoE > 1) {
                JOptionPane.showMessageDialog(this, "Los pesos deben estar en decimal entre 0.0 y 1.0 (ej. 0.20).");
                return;
            }
            if (notaC < 0 || notaC > 20 || notaE < 0 || notaE > 20) {
                JOptionPane.showMessageDialog(this, "Las notas deben estar entre 0.0 y 20.0.");
                return;
            }

            double pesoNuevo = pesoC + pesoE;
            Fase faseExistente = curso.buscarFase(numFase);
            double pesoAnteriorMismaFase = (faseExistente != null) ? faseExistente.calcularPesoFase() : 0.0;
            double pesoTotalProyectado = curso.calcularPesoRegistrado() - pesoAnteriorMismaFase + pesoNuevo;

            if (pesoTotalProyectado > 1.0001) {
                JOptionPane.showMessageDialog(this,
                        String.format("La suma de pesos del curso excedería el 100%% (actual sin esta fase: %.0f%%, nuevo: %.0f%%).",
                                (curso.calcularPesoRegistrado() - pesoAnteriorMismaFase) * 100,
                                pesoNuevo * 100));
                return;
            }

            NotaContinua continua = new NotaContinua("Continua " + numFase, pesoC, notaC);
            ExamenParcial examen = new ExamenParcial(numFase, "Examen " + numFase, pesoE, notaE);
            Fase fase = new Fase(numFase, continua, examen);

            curso.agregarOActualizarFase(fase);
            txtNotaContinua.setText("");
            txtNotaExamen.setText("");
            refrescarCombosYLista();
            JOptionPane.showMessageDialog(this, "Fase " + numFase + " guardada para " + curso.getNombre() + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifica que todos los pesos y notas sean números válidos.");
        }
    }

    private void aplicarSustitutorioUI() {
        String nombreCurso = (String) cbCursosAnalisis.getSelectedItem();
        Curso curso = gestor.buscarCurso(nombreCurso);
        if (curso == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso.");
            return;
        }

        if (!curso.puedeRendirSustitutorio()) {
            JOptionPane.showMessageDialog(this,
                    "El sustitutorio solo aplica después de tener cerradas Fase 1 y Fase 2, y antes de registrar Fase 3.");
            return;
        }

        try {
            double notaSusti = Double.parseDouble(txtNotaSustitutorio.getText().trim());
            if (notaSusti < 0 || notaSusti > 20) {
                JOptionPane.showMessageDialog(this, "La nota del sustitutorio debe estar entre 0.0 y 20.0.");
                return;
            }

            ExamenParcial objetivo = curso.obtenerExamenObjetivoSustitutorio();
            double notaAnterior = objetivo.getNota();
            curso.registrarSustitutorio(notaSusti);

            JOptionPane.showMessageDialog(this,
                    "Sustitutorio aplicado a " + objetivo.getEtiqueta() +
                    "\nNota anterior: " + notaAnterior + " -> Nueva nota: " + objetivo.getNota());
            mostrarAnalisis();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa una nota válida para el sustitutorio.");
        }
    }

    private void mostrarAnalisis() {
        String nombreCurso = (String) cbCursosAnalisis.getSelectedItem();
        Curso curso = gestor.buscarCurso(nombreCurso);
        if (curso == null) {
            txtAreaReporte.setText("No hay cursos registrados todavía.");
            return;
        }

        double acumulado = curso.calcularPromedioPonderadoActual();
        double pesoRegistrado = curso.calcularPesoRegistrado();
        double pesoRestante = curso.calcularPesoRestante();
        double faltante = curso.calcularPuntosFaltantes();

        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("UNSA - ANÁLISIS DE CURSO: ").append(curso.getNombre().toUpperCase()).append("\n");
        sb.append("Créditos: ").append(curso.getCreditos()).append("\n");
        sb.append("====================================================\n\n");

        for (Fase f : curso.getListaFases()) {
            sb.append(String.format("Fase %d -> Continua (peso %.0f%%): %.2f [pond: %.2f] | Examen %d (peso %.0f%%): %.2f [pond: %.2f]%n",
                    f.getNumeroFase(),
                    f.getContinua().getPeso() * 100,
                    f.getContinua().getNota(),
                    f.getContinua().calcularAporte(),
                    f.getExamen().getNumeroParcial(),
                    f.getExamen().getPeso() * 100,
                    f.getExamen().getNota(),
                    f.getExamen().calcularAporte()
            ));
        }

        sb.append("\n----------------------------------------------------\n");
        sb.append(String.format("Puntaje ponderado acumulado: %.2f%n", acumulado));
        sb.append(String.format("Peso registrado: %.0f%% | Peso restante: %.0f%%%n", pesoRegistrado * 100, pesoRestante * 100));
        sb.append(String.format("Nota mínima aprobatoria: %.2f%n", curso.getNotaAprobatoriaMinima()));

        if (faltante <= 0) {
            sb.append("Estado: APROBADO (superaste el puntaje mínimo requerido).\n");
        } else {
            sb.append(String.format("Puntos ponderados faltantes para 10.5: %.2f%n", faltante));
            if (pesoRestante > 0) {
                double notaPromedioNecesaria = faltante / pesoRestante;
                sb.append(String.format("Nota promedio necesaria en el peso restante: %.2f%n", notaPromedioNecesaria));
                if (notaPromedioNecesaria > 20.0) {
                    sb.append("Alerta: supera la nota máxima (20.00).\n");
                }
            }
        }

        sb.append("\nSustitutorio (solo entre Fase 2 y Fase 3, reemplaza menor de Parcial 1 o 2): ");
        sb.append(curso.puedeRendirSustitutorio() ? "DISPONIBLE AHORA" : "NO DISPONIBLE EN ESTE MOMENTO").append("\n");

        if (curso.getSustitutorio() != null && curso.getSustitutorio().isAplicado()) {
            ExamenParcial rep = curso.getSustitutorio().getExamenReemplazado();
            sb.append(String.format("Último sustitutorio aplicado en %s: %.2f -> %.2f%n",
                    rep.getEtiqueta(), rep.getNotaOriginal(), rep.getNota()));
        }

        sb.append(String.format("%nPromedio Ponderado Semestral (por créditos): %.2f%n", gestor.calcularPromedioPonderadoSemestral()));
        txtAreaReporte.setText(sb.toString());
    }

    private void refrescarCombosYLista() {
        String selFase = (String) cbCursosFase.getSelectedItem();
        String selAnalisis = (String) cbCursosAnalisis.getSelectedItem();

        cbCursosFase.removeAllItems();
        cbCursosAnalisis.removeAllItems();
        modeloListaCursos.clear();

        for (Curso c : gestor.getListaCursos()) {
            cbCursosFase.addItem(c.getNombre());
            cbCursosAnalisis.addItem(c.getNombre());
            modeloListaCursos.addElement(String.format("%-28s | Cred: %d | Fases: %d/3 | Acum: %.2f",
                    c.getNombre(), c.getCreditos(), c.getListaFases().size(), c.calcularPromedioPonderadoActual()));
        }

        if (selFase != null) cbCursosFase.setSelectedItem(selFase);
        if (selAnalisis != null) cbCursosAnalisis.setSelectedItem(selAnalisis);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(fondo);
        btn.setForeground(textoColor);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestorAcademicoGUI::new);
    }
}