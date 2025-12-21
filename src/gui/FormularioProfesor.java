package gui;
import service.ServiceProfesor;
import service.ServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioProfesor extends JPanel {
    private ServiceProfesor serviceProfesor;
    private PanelManager panelManager;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JButton botonGrabar;

    public FormularioProfesor(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.serviceProfesor = new ServiceProfesor();
        armarFormulario();
    }

    private void armarFormulario() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1.0; // Repartir altura verticalmente
        gbc.ipady = 15;    // Campos más altos

        // Inicializar componentes
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtEmail = new JTextField(20);
        botonGrabar = new JButton("Grabar Profesor");

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
        add(txtNombre, gbc);

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
        add(txtApellido, gbc);

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
        add(txtEmail, gbc);

        // Grabar
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Ocupa el ancho en su totalidad
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5); // Margen extra arriba

        botonGrabar.setPreferredSize(new Dimension(200, 40));
        add(botonGrabar, gbc);

        botonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grabarProfesor();
            }
        });
    }

    private void grabarProfesor() {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            serviceProfesor.crearProfesor(nombre, apellido, email);

            JOptionPane.showMessageDialog(this, "Profesor creado correctamente");
            limpiar();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
    }
}