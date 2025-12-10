package gui;
import Entidades.Abono;
import service.ServiceAlumno;
import service.ServiceAbono;
import service.ServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormularioAlumno extends JPanel {
    private ServiceAlumno serviceAlumno;
    private ServiceAbono serviceAbono;
    private PanelManager panelManager;

    private JTextField jTextFieldNombre;
    private JTextField jTextFieldApellido;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldLimiteCursos;

    private JComboBox<Abono> comboAbono;
    private JButton btnRefrescarAbono;

    private JButton jButtonGrabar;

    public FormularioAlumno(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.serviceAlumno = new ServiceAlumno();
        this.serviceAbono = new ServiceAbono();
        armarFormulario();
    }

    public void armarFormulario() {
        // 6 filas y 2 columnas de layout
        this.setLayout(new GridLayout(6, 2, 10, 10));
        this.setBackground(Color.white);

        // Inicializar campos
        jTextFieldNombre = new JTextField(20);
        jTextFieldApellido = new JTextField(20);
        jTextFieldEmail = new JTextField(20);
        jTextFieldLimiteCursos = new JTextField(20);

        // Inicializar combo y boton de Refrescar
        comboAbono = new JComboBox<>();
        btnRefrescarAbono = new JButton("Refrescar"); // <-- Inicializar

        // Cargar los datos iniciales
        cargarAbonos();

        jButtonGrabar = new JButton("Grabar Alumno");

        this.add(new JLabel("Nombre:"));
        this.add(jTextFieldNombre);

        this.add(new JLabel("Apellido:"));
        this.add(jTextFieldApellido);

        this.add(new JLabel("Email:"));
        this.add(jTextFieldEmail);

        this.add(new JLabel("Límite de Cursos:"));
        this.add(jTextFieldLimiteCursos);

        this.add(new JLabel("Abono:"));

        // Pongo el combo y el boton juntos en un panel chiquito
        JPanel panelAbono = new JPanel(new BorderLayout());
        panelAbono.add(comboAbono, BorderLayout.CENTER);
        panelAbono.add(btnRefrescarAbono, BorderLayout.EAST);

        this.add(panelAbono); // Agrego el panel chiquito en lugar del combo solo
        this.add(new JLabel("")); // Espacio vacío para alinear
        this.add(jButtonGrabar);

        // Botones
        btnRefrescarAbono.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAbonos(); // Vuelve a consultar la base de datos
                JOptionPane.showMessageDialog(FormularioAlumno.this, "Lista de abonos actualizada.");
            }
        });

        jButtonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grabarAlumno();
            }
        });
    }

    private void cargarAbonos(){
        try {
            // Abono "Ninguno"
            Abono abonoNinguno = new Abono(0, "Ninguno", 0, 0);

            DefaultComboBoxModel<Abono> abonoModel = new DefaultComboBoxModel<>();
            abonoModel.addElement(abonoNinguno);

            // Traer de la base de datos
            List<Abono> abonos = serviceAbono.buscarTodosLosAbonos();
            for (Abono ab : abonos) {
                abonoModel.addElement(ab);
            }
            comboAbono.setModel(abonoModel);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar abonos: " + e.getMessage());
        }
    }

    private void grabarAlumno() {
        try {
            String nombre = jTextFieldNombre.getText();
            String apellido = jTextFieldApellido.getText();
            String email = jTextFieldEmail.getText();
            int limite = Integer.parseInt(jTextFieldLimiteCursos.getText());

            Abono abonoSeleccionado = (Abono) comboAbono.getSelectedItem();
            int idAbono = abonoSeleccionado.getIdAbono();

            serviceAlumno.crearAlumno(nombre, apellido, email, limite, idAbono);
            JOptionPane.showMessageDialog(this, "Alumno guardado correctamente");

            // Limpiar campos
            jTextFieldNombre.setText("");
            jTextFieldApellido.setText("");
            jTextFieldEmail.setText("");
            jTextFieldLimiteCursos.setText("");
            comboAbono.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El límite de cursos debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException s) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + s.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}