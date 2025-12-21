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
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1.0;
        gbc.ipady = 15;

        // Inicializar campos
        jTextFieldNombre = new JTextField(20);
        jTextFieldApellido = new JTextField(20);
        jTextFieldEmail = new JTextField(20);
        jTextFieldLimiteCursos = new JTextField(20);

        // Inicializar combo y boton de Refrescar
        comboAbono = new JComboBox<>();
        btnRefrescarAbono = new JButton("Refrescar");
        jButtonGrabar = new JButton("Grabar Alumno");

        // Cargar los datos iniciales
        cargarAbonos();

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0; // Label fijo
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Campo se estira
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jTextFieldNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jTextFieldApellido, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jTextFieldEmail, gbc);

        // Límite Cursos
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Límite de Cursos:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jTextFieldLimiteCursos, gbc);

        // Abono (combo + botón)
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Abono:"), gbc);

        // Panel interno para agrupar combo y botón
        JPanel panelAbono = new JPanel(new BorderLayout(5, 0));
        panelAbono.setBackground(Color.white);
        panelAbono.add(comboAbono, BorderLayout.CENTER);
        panelAbono.add(btnRefrescarAbono, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // El panel entero se estira
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelAbono, gbc);

        // Grabar
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2; // Ocupa el ancho en su totalidad
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5); // Margen extra arriba

        jButtonGrabar.setPreferredSize(new Dimension(200, 40));
        add(jButtonGrabar, gbc);

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