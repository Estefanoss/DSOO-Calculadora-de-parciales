import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GestorAcademicoGUI extends JFrame{
    private JLabel Curso, Fase, CantFase, NotaContinua,NotaParcial;
    private JTextField txtCurso, txtNumFase, txtCantFase,txtNotaContinua,txtNotaParcial;
    private JButton btnCurso, btnFase;
    private JPanel PanelCursos;
    private JComboBox<String> ComboCursos;
    private GestorAcademico gestor = new GestorAcademico();
    public GestorAcademicoGUI(){
        setSize(900,600);
        setTitle("Organizador de Tareas");
        setLayout(new GridLayout(1,2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createsContents();
        setVisible(true);
    } 
    public void createsContents(){
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
                            if(nombre.isEmpty() || cFase.isEmpty()){
                                JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Completa todos los campos");
                            }
                            else{
                                int cantFase;
                                try{
                                    cantFase = Integer.parseInt(cFase);
                                    Curso curso = new Curso(nombre);
                                    for (int i=0;i<cantFase;i++){
                                        curso.agregarFase(new Fase(i+1,null , null));
                                    }
                                    gestor.agregarCurso(curso);
                                    txtCurso.setText("");
                                    txtCantFase.setText("");
                                    actualizarPanelCursos();
                                    actualizarComboCursos();
                                }catch(NumberFormatException ex){
                                    JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Ingrese un numero de fases");
                                }
                                
                            }
                        }
                    });
                    ButtonCourse.add(btnCurso);
            NewCourseElements.add(ButtonCourse);
        NewCourse.add(NewCourseElements);
           
        JPanel NewFase = new JPanel();
            JPanel NewFaseElements = new JPanel(new GridLayout(5,1));
                JPanel NewFaseComboBox = new JPanel();
                    ComboCursos = new JComboBox<>();
                    NewFaseComboBox.add(ComboCursos);
                NewFaseElements.add(NewFaseComboBox);
                JPanel NewFaseNum = new JPanel();   
                    Fase = new JLabel("  N. de la fase: ");
                    txtNumFase = new JTextField(15);
                    NewFaseNum.add(Fase);
                    NewFaseNum.add(txtNumFase);
                NewFaseElements.add(NewFaseNum);
                JPanel NewFaseNotaParcial = new JPanel();
                    NotaParcial = new JLabel("  Nota Parcial:  ");
                    txtNotaParcial = new JTextField(15);
                    NewFaseNotaParcial.add(NotaParcial);
                    NewFaseNotaParcial.add(txtNotaParcial);
                NewFaseElements.add(NewFaseNotaParcial);
                JPanel NewFaseNotaContinua = new JPanel();
                    NotaContinua = new JLabel("Nota Continua:");
                    txtNotaContinua = new JTextField(15);
                    NewFaseNotaContinua.add(NotaContinua);
                    NewFaseNotaContinua.add(txtNotaContinua);
                NewFaseElements.add(NewFaseNotaContinua);
                JPanel ButtonFase = new JPanel();
                    btnFase = new JButton("Agregar Notas");
                    btnFase.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            int nFase;
                            double NP,NC;
                            try {
                                nFase = Integer.parseInt(txtNumFase.getText());
                                NP = Double.parseDouble(txtNotaParcial.getText());
                                NC = Double.parseDouble(txtNotaContinua.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(GestorAcademicoGUI.this, "Ingrese numeros, no letras");
                            } 
                        }
                    });
                    ButtonFase.add(btnFase);
                NewFaseElements.add(ButtonFase);
            NewFase.add(NewFaseElements);
        JPanel LeftPanel = new JPanel();
            LeftPanel.add(NewCourse);
            LeftPanel.add(NewFase);
        JPanel RightPanel = new JPanel();
            PanelCursos = new JPanel(new GridLayout(0,1,10,10));
            RightPanel.add(PanelCursos);
        add(LeftPanel);
        add(RightPanel);
    
    }
    private void actualizarPanelCursos(){
        PanelCursos.removeAll();
        JPanel PanelCurso = new JPanel(new GridLayout(0,1,5,5));
        for (Curso c : gestor.getListaCursos()) { 
            JLabel cLabel = new JLabel(c.getNombre()+"  "+"N.Fase | N.Continua | N.Parcial");
            PanelCurso.add(cLabel);
            for(Fase f : c.getListaFases()){
                NotaContinua NC = f.getContinua();
                ExamenParcial NP = f.getExamen();
                String textoNC = (NC != null) ? String.valueOf(NC.getNota()) : "Sin nota";
                String textoNP = (NP != null) ? String.valueOf(NP.getNota()) : "Sin nota";
                JLabel fLabel = new JLabel("Fase " + f.getNumeroFase() + "   |   " + textoNC + "   |   " + textoNP);
                PanelCurso.add(fLabel);
            }
            PanelCursos.add(PanelCurso);
            PanelCurso = new JPanel(new GridLayout(0,1,5,5));
        }
        PanelCursos.revalidate();
        PanelCursos.repaint();
    }
    private void actualizarComboCursos(){
        String[] nombres = new String[gestor.getListaCursos().size()+1];

        nombres[0] = "Seleccione un curso"; 
        int c=0;
        for(Curso curso:gestor.getListaCursos()){
            nombres[c+1] = curso.getNombre();
            c++;
        }
        DefaultComboBoxModel<String> actual = new DefaultComboBoxModel<>(nombres); 
        ComboCursos.setModel(actual);
    }
    public static void main(String[] args) {
        new GestorAcademicoGUI();
    }
}
