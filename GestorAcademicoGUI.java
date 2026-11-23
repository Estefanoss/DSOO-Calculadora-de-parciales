import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GestorAcademicoGUI extends JFrame{
    private JLabel Curso, Fase, CantFase, NotaContinua,NotaParcial, PesoContinua, PesoParcial;
    private JTextField txtCurso, txtNumFase, txtCantFase,txtNotaContinua,txtNotaParcial,txtPesoContinua,txtPesoParcial;
    private JButton btnCurso, btnFase, btnEliminarCurso, btnGuardar, btnCargar;
    private JPanel PanelCursos,SummaryCourses;
    private JComboBox<String> ComboCursos;
    private GestorAcademico gestor = new GestorAcademico();
    public GestorAcademicoGUI(){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setTitle("SEMESTRE ESTADO");
        setLayout(new GridLayout(1,2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createsContents();
        setVisible(true);
    } 
    public void createsContents(){
        
        JPanel LeftPanel = new JPanel(new GridLayout(2,1));
            JPanel MenuPanel = new JPanel();
                JPanel NewCourse = new JPanel();
                    JPanel NewCourseElements = new JPanel(new GridLayout(3,1));
                        JPanel NewCourseName = new JPanel();
                            Curso = new JLabel("Nombre del curso:");
                            txtCurso = new JTextField(15);
                            NewCourseName.add(Curso);
                            NewCourseName.add(txtCurso);
                    NewCourseElements.add(NewCourseName);
                        JPanel NewCourseFase = new JPanel();
                            CantFase = new JLabel("Numero de fases: ");
                            txtCantFase = new JTextField(15);
                            NewCourseFase.add(CantFase);
                            NewCourseFase.add(txtCantFase);
                    NewCourseElements.add(NewCourseFase);
                        JPanel ButtonCourse = new JPanel();
                            btnCurso = new JButton("Agregar Curso");
                            btnCurso.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    String nombre = txtCurso.getText().trim();
                                    String cFase = txtCantFase.getText().trim();
                                    if(gestor.getListaCursos().size()==12){
                                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Se alcanzo el limite de cursos, elimine antes de agregar");
                                    }
                                    else{
                                        if(gestor.buscarCurso(nombre)!=null){
                                            JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Este curso ya existe");
                                            txtCurso.setText("");
                                            txtCantFase.setText("");
                                        }
                                        else{
                                            if((nombre.isEmpty() || cFase.isEmpty())){
                                                JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Completa todos los campos");
                                            }
                                            else{
                                                int cantFase;
                                                try{
                                                    cantFase = Integer.parseInt(cFase);
                                                    Curso curso = new Curso(nombre);
                                                    for (int i=0;i<cantFase;i++){
                                                        curso.agregarFase(new Fase(i+1, new NotaContinua(1,-1),new ExamenParcial(i+1,1,-1)));
                                                    }
                                                    gestor.agregarCurso(curso);
                                                    txtCurso.setText("");
                                                    txtCantFase.setText("");
                                                    if(gestor.getListaCursos().size() > 0){
                                                        NewFaseEnabled(true);
                                                    }
                                                    actualizarPanelCursos();
                                                    actualizarComboCursos();
                                                    actualizarCurrentStatus();
                                                }catch(NumberFormatException ex){
                                                    JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Ingrese un numero de fases");
                                                }
                                                
                                            }
                                        }
                                    }
                                }
                            });
                            ButtonCourse.add(btnCurso);
                    NewCourseElements.add(ButtonCourse);
                NewCourse.add(NewCourseElements);
            MenuPanel.add(NewCourse);

                JPanel NewFase = new JPanel();
                    JPanel NewFaseElements = new JPanel(new GridLayout(7,1));
                        JPanel NewFaseComboBox = new JPanel();
                            ComboCursos = new JComboBox<>();
                            ComboCursos.setEnabled(false);
                            btnEliminarCurso = new JButton("Eliminar");
                            btnEliminarCurso.setEnabled(false);
                            btnEliminarCurso.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    String nombreCurso = (String) ComboCursos.getSelectedItem();
                                    if(nombreCurso.equals("Seleccione un curso")){
                                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Seleccione un curso para poder eliminar");
                                        txtCurso.setText("");
                                        txtCantFase.setText("");
                                    }
                                    else{
                                        gestor.eliminarCurso(nombreCurso);
                                        actualizarComboCursos();
                                        actualizarPanelCursos();
                                        actualizarCurrentStatus();
                                        if(gestor.getListaCursos().size() == 0){
                                            NewFaseEnabled(false);
                                        }
                                    }
                                }
                            });
                            NewFaseComboBox.add(ComboCursos);
                            NewFaseComboBox.add(btnEliminarCurso);
                        NewFaseElements.add(NewFaseComboBox);
                        JPanel NewFaseNum = new JPanel();   
                            Fase = new JLabel("N. de la fase:   ");
                            txtNumFase = new JTextField(15);
                            txtNumFase.setEditable(false);
                            NewFaseNum.add(Fase);
                            NewFaseNum.add(txtNumFase);
                        NewFaseElements.add(NewFaseNum);
                        JPanel NewFaseNotaParcial = new JPanel();
                            NotaParcial = new JLabel("Nota Parcial:    ");
                            txtNotaParcial = new JTextField(15);
                            txtNotaParcial.setEditable(false);
                            NewFaseNotaParcial.add(NotaParcial);
                            NewFaseNotaParcial.add(txtNotaParcial);
                        NewFaseElements.add(NewFaseNotaParcial);
                        JPanel NewFasePesoParcial = new JPanel();
                            PesoParcial = new JLabel("Peso Parcial:   ");
                            txtPesoParcial = new JTextField(15);
                            txtPesoParcial.setEditable(false);
                            NewFasePesoParcial.add(PesoParcial);
                            NewFasePesoParcial.add(txtPesoParcial);
                        NewFaseElements.add(NewFasePesoParcial);
                        JPanel NewFaseNotaContinua = new JPanel();
                            NotaContinua = new JLabel("Nota Continua:");
                            txtNotaContinua = new JTextField(15);
                            txtNotaContinua.setEditable(false);
                            NewFaseNotaContinua.add(NotaContinua);
                            NewFaseNotaContinua.add(txtNotaContinua);
                        NewFaseElements.add(NewFaseNotaContinua);
                        JPanel NewFasePesoContinua = new JPanel();
                            PesoContinua = new JLabel("Peso Continua:"); 
                            txtPesoContinua = new JTextField(15);
                            txtPesoContinua.setEditable(false);
                            NewFasePesoContinua.add(PesoContinua);
                            NewFasePesoContinua.add(txtPesoContinua);
                        NewFaseElements.add(NewFasePesoContinua);
                        JPanel ButtonFase = new JPanel();
                            btnFase = new JButton("Modificar Notas");
                            btnFase.setEnabled(false);
                            btnFase.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    int nFase;
                                    double NP,NC,PNP,PNC;
                                    try {
                                        if(txtNumFase.getText().isEmpty() || txtNotaParcial.getText().isEmpty() || txtNotaContinua.getText().isEmpty() || txtPesoParcial.getText().isEmpty() || txtPesoContinua.getText().isEmpty()){
                                            JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Complete todos los campos");
                                        }
                                        else{
                                            nFase = Integer.parseInt(txtNumFase.getText());
                                            NP = Double.parseDouble(txtNotaParcial.getText());
                                            NC = Double.parseDouble(txtNotaContinua.getText());
                                            PNP = Double.parseDouble(txtPesoParcial.getText());
                                            PNC = Double.parseDouble(txtPesoContinua.getText());
                                            String nombre = (String) ComboCursos.getSelectedItem();
                                            if (nombre.equals("Seleccione un curso"))
                                                JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Selecione un curso");
                                            else{
                                                Curso curso = gestor.buscarCurso(nombre);
                                                curso.getListaFases().get(nFase-1).getExamen().setNota(NP);
                                                curso.getListaFases().get(nFase-1).getContinua().setNota(NC);
                                                curso.getListaFases().get(nFase-1).getExamen().setPeso(PNP);
                                                curso.getListaFases().get(nFase-1).getContinua().setPeso(PNC);
                                                curso.getListaFases().get(nFase-1).setCerrada(true);
                                                txtNumFase.setText("");
                                                txtNotaParcial.setText("");
                                                txtNotaContinua.setText("");
                                                txtPesoContinua.setText("");
                                                txtPesoParcial.setText("");
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Ingrese numeros, no letras");
                                    } 
                                    finally{
                                        actualizarPanelCursos();
                                        actualizarCurrentStatus();
                                    }
                                }
                            });
                            ButtonFase.add(btnFase);
                    NewFaseElements.add(ButtonFase);
                NewFase.add(NewFaseElements);
            MenuPanel.add(NewFase);
        LeftPanel.add(MenuPanel);
            JPanel CurrentStatus = new JPanel();
                SummaryCourses = new JPanel(new GridLayout(0,3,5,5));
                JLabel SummaryCoursesMensaje = new JLabel("Estado Actual de los Cursos del Semestre");
                SummaryCoursesMensaje.setFont(new Font("SansSerif", Font.BOLD, 15));
                SummaryCoursesMensaje.setForeground(new Color(20, 40, 80));
            CurrentStatus.add(SummaryCoursesMensaje);
            CurrentStatus.add(SummaryCourses);

        LeftPanel.add(CurrentStatus);
        JPanel RightPanel = new JPanel();
            PanelCursos = new JPanel(new GridLayout(0,2,10,6));
            JLabel PanelCursosMensaje = new JLabel("Lista de Cursos del Semestre");
            PanelCursosMensaje.setFont(new Font("SansSerif", Font.BOLD, 15));
            PanelCursosMensaje.setForeground(new Color(20, 40, 80));
            JPanel btnArchivos= new JPanel();
                btnGuardar = new JButton("Guardar");
                btnCargar = new JButton("Cargar");
            btnArchivos.add(btnGuardar);
            btnArchivos.add(btnCargar);
        RightPanel.add(PanelCursosMensaje);
        RightPanel.add(PanelCursos);
        RightPanel.add(btnArchivos);
        add(LeftPanel);
        add(RightPanel);
    
    }
    private void actualizarPanelCursos(){
        PanelCursos.removeAll();
        JPanel PanelCursoN = new JPanel(new GridLayout(0,1,1,1));
        double valorNotaFinal = 0;
        for (Curso c : gestor.getListaCursos()) { 
            String nombre = c.getNombre();
            JLabel nombreLabel = new JLabel(nombre);
            JLabel datosLabel = new JLabel("    (%) N.Fase  |  N.Parcial (%)  |  N.Continua (%)  |  Aporte de Fase");
            PanelCursoN.add(nombreLabel);
            PanelCursoN.add(datosLabel);
            for(Fase f : c.getListaFases()){
                NotaContinua NC = f.getContinua();
                ExamenParcial NP = f.getExamen();
                String textoNC = (NC.getNota() != -1) ? " "+String.valueOf(NC.getNota())+" " : "Sin nota";
                String textoNP = (NP.getNota() != -1) ? " "+String.valueOf(NP.getNota())+" " : "Sin nota";
                String textoPNC = (NC.getPeso() != 1) ? " "+String.valueOf(NC.getPeso()*100)+"%" : "?%";
                String textoPNP = (NP.getPeso() != 1) ? " "+String.valueOf(NP.getPeso()*100)+"%" : "?%";
                String textoAporteFase = f.calcularAporteTotal()+"pts"; 
                valorNotaFinal += f.calcularAporteTotal();
                int valorPT = ((NC.getPeso() != 1) ? (int) Math.round(NC.getPeso()*100) : 0) + ((NP.getPeso() != 1) ? (int) Math.round(NP.getPeso()*100): 0);
                JLabel fLabel = new JLabel("    ("+((valorPT!=0)? valorPT:"?")+"%) Fase "  + f.getNumeroFase() + " |   " + textoNP+ " (" + textoPNP+")" + "   |   " + textoNC +" (" + textoPNC + ")"+"  |  "+ textoAporteFase );
                fLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                fLabel.setForeground(new Color(20, 40, 80));
                PanelCursoN.add(fLabel);
                PanelCursos.add(PanelCursoN);
            }
            JLabel aporteLabel = new JLabel("    Nota Final Acumulada:  "+valorNotaFinal);
            aporteLabel.setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
            aporteLabel.setForeground(new Color(20, 40, 80));
            PanelCursoN.add(aporteLabel);
            PanelCursoN = new JPanel(new GridLayout(0,1,5,5));
        }
        PanelCursos.revalidate();
        PanelCursos.repaint();
    }
    private void actualizarComboCursos(){
        String[] nombres = new String[gestor.getListaCursos().size()+1];
        if(gestor.getListaCursos().size()!=0){
            nombres[0] = "Seleccione un curso"; 
        }
        int c=0;
        for(Curso curso:gestor.getListaCursos()){
            nombres[c+1] = curso.getNombre();
            c++;
        }
        DefaultComboBoxModel<String> actual = new DefaultComboBoxModel<>(nombres); 
        ComboCursos.setModel(actual);
    }
    private void actualizarCurrentStatus(){
        SummaryCourses.removeAll();
        JPanel PanelCursoN = new JPanel(new GridLayout(0,1,1,1));
        double valorNotaFinal = 0;
        for (Curso c : gestor.getListaCursos()) { 
            String nombre = c.getNombre();
            JLabel nombreLabel = new JLabel(nombre);
            PanelCursoN.add(nombreLabel);
            for(Fase f : c.getListaFases()){ 
                NotaContinua NC = f.getContinua();
                valorNotaFinal += f.calcularAporteTotal();
            }
        
            JLabel fLabel = new JLabel();
            if(valorNotaFinal<10.5 && ExamenSustitutorio.esElegible(c.getListaFases())){
                fLabel = new JLabel("Desaprobado");
                fLabel.setForeground(Color.RED);
            }
            else if(valorNotaFinal>10.5 && ExamenSustitutorio.esElegible(c.getListaFases())){
                fLabel = new JLabel("Aprobado");
                fLabel.setForeground(new Color(64, 224, 208));
            }
            else{
                fLabel = new JLabel("Esperando calificaciones");
            }
            fLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            PanelCursoN.add(fLabel);
            SummaryCourses.add(PanelCursoN);
            JLabel aporteLabel = new JLabel("Nota Final Acumulada:  "+valorNotaFinal);
            aporteLabel.setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
            aporteLabel.setForeground(new Color(20, 40, 80));
            PanelCursoN.add(aporteLabel);
            PanelCursoN = new JPanel(new GridLayout(0,1,5,5));
            valorNotaFinal = 0;
        }
        SummaryCourses.revalidate();
        SummaryCourses.repaint();
    }
    private void NewFaseEnabled(boolean v){
        ComboCursos.setEnabled(v);
        btnEliminarCurso.setEnabled(v);
        btnFase.setEnabled(v);
        txtNumFase.setEditable(v);
        txtNotaParcial.setEditable(v);
        txtPesoParcial.setEditable(v);
        txtNotaContinua.setEditable(v);
        txtPesoContinua.setEditable(v);
        txtNumFase.setText("");
        txtNotaParcial.setText("");
        txtPesoParcial.setText("");
        txtNotaContinua.setText("");
        txtPesoContinua.setText("");
    }
    public static void main(String[] args) {
        new GestorAcademicoGUI();
    }
}
