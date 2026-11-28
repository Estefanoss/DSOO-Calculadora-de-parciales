import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GestorAcademicoGUI extends JFrame {

    private static final Color COLOR_ROJO_UNSA = new Color(138, 21, 36);
    private static final Color COLOR_DORADO = new Color(212, 160, 23);
    private static final Color COLOR_FONDO = new Color(245, 246, 248);
    private static final Color COLOR_BLANCO = Color.WHITE;

    private final GestorAcademico gestor = new GestorAcademico();

    private JTextField txtNombreCurso, txtCreditosCurso;
    private JComboBox<String> cbCursos;
    private JComboBox<Integer> cbFase;
    private JTextField txtPesoC, txtNotaC, txtPesoE, txtNotaE, txtSusti;
    private DefaultListModel<String> modeloCursos;
    private JTextArea txtResumenDinamico;

    public GestorAcademicoGUI() {
        new File("datos").mkdirs();
        RepositorioBD.inicializar();
        for (Curso c : RepositorioBD.cargarTodo()) {
            gestor.agregarCurso(c);
        }

        setTitle("UNSA — Simulador Académico Local");
        setSize(980, 640);
        setMinimumSize(new Dimension(840, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI();
        actualizarTodoDinamico();
        setVisible(true);
    }

    private void construirUI() {
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(COLOR_FONDO);

        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setBackground(COLOR_ROJO_UNSA);
        top.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel titulo = new JLabel("UNSA — SIMULADOR ACADÉMICO (BD LOCAL + ARCHIVOS)");
        titulo.setForeground(COLOR_BLANCO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));

        JPanel barraArchivos = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        barraArchivos.setOpaque(false);

        ManejadorEventos listener = new ManejadorEventos();
        String[] botonesArchivos = {
            "Guardar .TXT", "Leer .TXT",
            "Guardar .DAT", "Leer .DAT",
            "Guardar .OBJ", "Cargar .OBJ"
        };
        for (String texto : botonesArchivos) {
            JButton b = new JButton(texto);
            b.setBackground(COLOR_DORADO);
            b.setFocusPainted(false);
            b.addActionListener(listener);
            barraArchivos.add(b);
        }

        top.add(titulo, BorderLayout.NORTH);
        top.add(barraArchivos, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelCarga(listener), crearPanelResumen());
        split.setDividerLocation(430);
        add(split, BorderLayout.CENTER);
    }

    private JPanel crearPanelCarga(ActionListener listener) {
        JPanel cont = new JPanel();
        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
        cont.setBorder(new EmptyBorder(8, 8, 8, 8));
        cont.setBackground(COLOR_FONDO);

        JPanel pCurso = new JPanel(new GridLayout(3, 2, 6, 6));
        pCurso.setBorder(BorderFactory.createTitledBorder("1. Curso"));
        pCurso.setBackground(COLOR_BLANCO);
        txtNombreCurso = new JTextField();
        txtCreditosCurso = new JTextField("4");
        JButton btnAddCurso = new JButton("Agregar Curso");
        btnAddCurso.setBackground(COLOR_ROJO_UNSA);
        btnAddCurso.setForeground(COLOR_BLANCO);
        btnAddCurso.addActionListener(listener);

        pCurso.add(new JLabel("Nombre:"));
        pCurso.add(txtNombreCurso);
        pCurso.add(new JLabel("Créditos:"));
        pCurso.add(txtCreditosCurso);
        pCurso.add(new JLabel(""));
        pCurso.add(btnAddCurso);

        JPanel pFase = new JPanel(new GridLayout(7, 2, 6, 6));
        pFase.setBorder(BorderFactory.createTitledBorder("2. Fase (1, 2 o 3) y Pesos"));
        pFase.setBackground(COLOR_BLANCO);
        cbCursos = new JComboBox<>();
        cbCursos.addActionListener(listener);
        cbFase = new JComboBox<>(new Integer[]{1, 2, 3});
        txtPesoC = new JTextField("0.20");
        txtNotaC = new JTextField();
        txtPesoE = new JTextField("0.10");
        txtNotaE = new JTextField();
        JButton btnGuardarFase = new JButton("Guardar y Actualizar");
        btnGuardarFase.setBackground(COLOR_ROJO_UNSA);
        btnGuardarFase.setForeground(COLOR_BLANCO);
        btnGuardarFase.addActionListener(listener);

        pFase.add(new JLabel("Curso:"));
        pFase.add(cbCursos);
        pFase.add(new JLabel("Fase:"));
        pFase.add(cbFase);
        pFase.add(new JLabel("Peso Continua (ej 0.20):"));
        pFase.add(txtPesoC);
        pFase.add(new JLabel("Nota Continua:"));
        pFase.add(txtNotaC);
        pFase.add(new JLabel("Peso Parcial (ej 0.10):"));
        pFase.add(txtPesoE);
        pFase.add(new JLabel("Nota Parcial:"));
        pFase.add(txtNotaE);
        pFase.add(new JLabel(""));
        pFase.add(btnGuardarFase);

        JPanel pSusti = new JPanel(new GridLayout(2, 2, 6, 6));
        pSusti.setBorder(BorderFactory.createTitledBorder("3. Sustitutorio (Pre-Fase 3)"));
        pSusti.setBackground(COLOR_BLANCO);
        txtSusti = new JTextField();
        JButton btnSusti = new JButton("Aplicar Sustitutorio");
        btnSusti.setBackground(COLOR_DORADO);
        btnSusti.addActionListener(listener);

        pSusti.add(new JLabel("Nota Sustitutorio:"));
        pSusti.add(txtSusti);
        pSusti.add(new JLabel(""));
        pSusti.add(btnSusti);

        cont.add(pCurso);
        cont.add(Box.createVerticalStrut(8));
        cont.add(pFase);
        cont.add(Box.createVerticalStrut(8));
        cont.add(pSusti);
        return cont;
    }

    private JPanel crearPanelResumen() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.setBackground(COLOR_FONDO);

        modeloCursos = new DefaultListModel<>();
        JList<String> lista = new JList<>(modeloCursos);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane spLista = new JScrollPane(lista);
        spLista.setPreferredSize(new Dimension(0, 120));
        spLista.setBorder(BorderFactory.createTitledBorder("Cursos Registrados (SQLite Local)"));

        txtResumenDinamico = new JTextArea();
        txtResumenDinamico.setEditable(false);
        txtResumenDinamico.setLineWrap(true);
        txtResumenDinamico.setWrapStyleWord(true);
        txtResumenDinamico.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane spResumen = new JScrollPane(txtResumenDinamico);
        spResumen.setBorder(BorderFactory.createTitledBorder("Resumen Dinámico / Contenido de Archivos"));

        p.add(spLista, BorderLayout.NORTH);
        p.add(spResumen, BorderLayout.CENTER);
        return p;
    }

    private class ManejadorEventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cbCursos) {
                actualizarTodoDinamico();
                return;
            }
            switch (e.getActionCommand()) {
                case "Agregar Curso" -> agregarCurso();
                case "Guardar y Actualizar" -> guardarFase();
                case "Aplicar Sustitutorio" -> aplicarSustitutorio();
                case "Guardar .TXT" -> {
                    try {
                        File f = new File("datos/reporte_unsa.txt");
                        GestorArchivos.guardarTexto(f, gestor, cursoSeleccionado());
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Guardado en " + f.getPath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Error TXT: " + ex.getMessage());
                    }
                }
                case "Leer .TXT" -> {
                    try {
                        txtResumenDinamico.setText(GestorArchivos.leerTexto(new File("datos/reporte_unsa.txt")));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "No se pudo leer TXT: " + ex.getMessage());
                    }
                }
                case "Guardar .DAT" -> {
                    try {
                        File f = new File("datos/resumen_binario.dat");
                        GestorArchivos.guardarBinario(f, gestor);
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Guardado en " + f.getPath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Error DAT: " + ex.getMessage());
                    }
                }
                case "Leer .DAT" -> {
                    try {
                        txtResumenDinamico.setText(GestorArchivos.leerBinario(new File("datos/resumen_binario.dat")));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "No se pudo leer DAT: " + ex.getMessage());
                    }
                }
                case "Guardar .OBJ" -> {
                    try {
                        File f = new File("datos/respaldo_cursos.obj");
                        GestorArchivos.guardarObjetos(f, gestor.getListaCursos());
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Objetos guardados en " + f.getPath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Error OBJ: " + ex.getMessage());
                    }
                }
                case "Cargar .OBJ" -> {
                    try {
                        ArrayList<Curso> cargados = GestorArchivos.leerObjetos(new File("datos/respaldo_cursos.obj"));
                        gestor.getListaCursos().clear();
                        for (Curso c : cargados) {
                            gestor.agregarCurso(c);
                            RepositorioBD.guardarCurso(c);
                            for (Fase fase : c.getListaFases()) {
                                RepositorioBD.guardarFase(c.getNombre(), fase);
                            }
                        }
                        refrescarCombo(cargados.isEmpty() ? null : cargados.get(0).getNombre());
                        actualizarTodoDinamico();
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Objetos cargados y sincronizados con SQLite.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "No se pudo cargar OBJ: " + ex.getMessage());
                    }
                }
            }
        }
    }

    private void agregarCurso() {
        String nombre = txtNombreCurso.getText().trim();
        if (nombre.isEmpty()) return;
        try {
            int cred = Integer.parseInt(txtCreditosCurso.getText().trim());
            if (cred <= 0) return;
            Curso c = new Curso(nombre, cred);
            gestor.agregarCurso(c);
            RepositorioBD.guardarCurso(c);
            txtNombreCurso.setText("");
            refrescarCombo(nombre);
            actualizarTodoDinamico();
        } catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(this, "Créditos inválidos.");
        }
    }

    private void guardarFase() {
        Curso curso = cursoSeleccionado();
        if (curso == null) return;
        try {
            int num = (Integer) cbFase.getSelectedItem();
            double pC = Double.parseDouble(txtPesoC.getText().trim());
            double nC = Double.parseDouble(txtNotaC.getText().trim());
            double pE = Double.parseDouble(txtPesoE.getText().trim());
            double nE = Double.parseDouble(txtNotaE.getText().trim());

            if (pC < 0 || pE < 0 || pC > 1 || pE > 1 || nC < 0 || nC > 20 || nE < 0 || nE > 20) {
                JOptionPane.showMessageDialog(this, "Pesos entre 0 y 1; notas entre 0 y 20.");
                return;
            }

            Fase anterior = curso.buscarFase(num);
            double pesoSinEsta = curso.calcularPesoRegistrado() - (anterior != null ? anterior.calcularPesoFase() : 0.0);
            if (pesoSinEsta + pC + pE > 1.0001) {
                JOptionPane.showMessageDialog(this, "La suma de pesos del curso excede 100%.");
                return;
            }

            Fase f = new Fase(num, new NotaContinua("Continua " + num, pC, nC), new ExamenParcial(num, "Examen " + num, pE, nE));
            curso.agregarOActualizarFase(f);
            RepositorioBD.guardarFase(curso.getNombre(), f);
            txtNotaC.setText("");
            txtNotaE.setText("");
            actualizarTodoDinamico();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Revisa los números ingresados.");
        }
    }

    private void aplicarSustitutorio() {
        Curso curso = cursoSeleccionado();
        if (curso == null) return;
        if (!curso.puedeRendirSustitutorio()) {
            JOptionPane.showMessageDialog(this, "Solo disponible tras Fase 1 y 2 cerradas, y antes de Fase 3.");
            return;
        }
        try {
            double notaS = Double.parseDouble(txtSusti.getText().trim());
            if (notaS < 0 || notaS > 20) return;
            curso.registrarSustitutorio(notaS);
            for (Fase f : curso.getListaFases()) {
                RepositorioBD.guardarFase(curso.getNombre(), f);
            }
            txtSusti.setText("");
            actualizarTodoDinamico();
        } catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(this, "Nota de sustitutorio inválida.");
        }
    }

    private Curso cursoSeleccionado() {
        return gestor.buscarCurso((String) cbCursos.getSelectedItem());
    }

    private void refrescarCombo(String seleccionarNombre) {
        cbCursos.removeAllItems();
        for (Curso c : gestor.getListaCursos()) {
            cbCursos.addItem(c.getNombre());
        }
        if (seleccionarNombre != null) {
            cbCursos.setSelectedItem(seleccionarNombre);
        }
    }

    private void actualizarTodoDinamico() {
        String actual = (String) cbCursos.getSelectedItem();
        if (cbCursos.getItemCount() != gestor.getListaCursos().size()) {
            refrescarCombo(actual);
        }
        modeloCursos.clear();
        for (Curso c : gestor.getListaCursos()) {
            modeloCursos.addElement(String.format("%-22s | Cred:%d | Acum:%.2f",
                    c.getNombre(), c.getCreditos(), c.calcularPromedioPonderadoActual()));
        }
        txtResumenDinamico.setText(ConstruirTextoAnalisis.generar(cursoSeleccionado(), gestor));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestorAcademicoGUI::new);
    }
}