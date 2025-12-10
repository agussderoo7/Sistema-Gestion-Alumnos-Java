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
        // Layout de 4 filas (3 datos + 1 botón), 2 columnas
        this.setLayout(new GridLayout(4, 2, 10, 10));
        this.setBackground(Color.white);

        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtEmail = new JTextField(20);
        botonGrabar = new JButton("Grabar Profesor");

        add(new JLabel("Nombre:"));
        add(txtNombre);

        add(new JLabel("Apellido:"));
        add(txtApellido);

        add(new JLabel("Email:"));
        add(txtEmail);

        add(new JLabel(""));
        add(botonGrabar);

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