package gui;
import service.ServiceAbono;
import service.ServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioAbono extends JPanel {
    private ServiceAbono serviceAbono;
    private JTextField textoNombre;
    private JTextField textoValor;
    private JTextField textoDescuento;
    private JButton botonGrabar;

    public FormularioAbono(PanelManager panelManager) {
        this.serviceAbono = new ServiceAbono();
        armarFormulario();
    }

    private void armarFormulario() {
        // GridBagLayout para consistencia visual con el resto de la aplicación
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1.0;
        gbc.ipady = 15;

        // Inicializar componentes
        textoNombre = new JTextField(20);
        textoValor = new JTextField(10);
        textoDescuento = new JTextField(10);
        botonGrabar = new JButton("Crear Abono");

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0; // Label fijo
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nombre del Abono:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Se estira el campo
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(textoNombre, gbc);

        // Valor
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Valor ($): (0 si es beca)"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(textoValor, gbc);

        // Descuento
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Descuento (%):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(textoDescuento, gbc);

        // Grabar
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Ocupa las 2 columnas
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5); // Margen extra arriba del botón

        botonGrabar.setPreferredSize(new Dimension(200, 40)); // Botón grande
        add(botonGrabar, gbc);

        // Botón
        botonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grabarAbono();
            }
        });
    }

    private void grabarAbono() {
        try {
            String nombre = textoNombre.getText();

            // Valido nombre
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double valor = Double.parseDouble(textoValor.getText());
            double descuento = Double.parseDouble(textoDescuento.getText());

            // Llamamos al servicio que cree
            serviceAbono.crearAbono(nombre, valor, descuento);

            JOptionPane.showMessageDialog(this, "Abono creado");
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor y Descuento deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        textoNombre.setText("");
        textoValor.setText("");
        textoDescuento.setText("");
    }
}

/* Diferencia entre "Valor" y "Descuento":
El valor indica cuanto paga el alumno por tener ese abono (de ahí el "0$ si es beca completa"), mientras que el descuento
es cuanto porcentaje se le descuenta del valor del curso.
 */