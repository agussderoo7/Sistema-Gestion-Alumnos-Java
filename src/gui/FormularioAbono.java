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
        // Layout: 4 filas, 2 columnas
        this.setLayout(new GridLayout(4, 2, 10, 10));
        this.setBackground(Color.white);

        // Inicializar
        textoNombre = new JTextField(20);
        textoValor = new JTextField(10);
        textoDescuento = new JTextField(10);
        botonGrabar = new JButton("Crear Abono");

        // Añadir componentes
        add(new JLabel("Nombre del Abono:"));
        add(textoNombre);

        add(new JLabel("Valor ($): (Poner 0 si es beca)"));
        add(textoValor);

        add(new JLabel("Descuento (%): (Ej: 50)"));
        add(textoDescuento);

        add(new JLabel("")); // Espacio vacío
        add(botonGrabar);

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