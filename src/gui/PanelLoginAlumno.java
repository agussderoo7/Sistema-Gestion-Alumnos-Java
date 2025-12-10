package gui;
import Entidades.Alumno;
import service.ServiceAlumno;
import service.ServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLoginAlumno extends JPanel{
    private PanelManager panelManager;
    private ServiceAlumno serviceAlumno;

    // Componentes
    private JTextField IdAlumno;
    private JButton botonEntrar;
    private JButton botonVolver;
    private JLabel lblTitulo;

    public PanelLoginAlumno(PanelManager manager) {
        this.panelManager = manager;
        this.serviceAlumno = new ServiceAlumno();
        armarPanelLogin();
    }

    public void armarPanelLogin(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblTitulo = new JLabel("Ingreso Alumno");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        add(lblTitulo, gbc);

        // Fila de "ID"
        gbc.gridwidth = 1; // Resetea a 1 columna
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST; // Alinea a la derecha
        add(new JLabel("Ingrese su ID de Alumno: "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Alinea a la izquierda
        IdAlumno = new JTextField(15);
        add(IdAlumno, gbc);

        // Fila de "Botones"
        JPanel panelBotones = new JPanel();
        botonVolver = new JButton("Volver");
        botonEntrar = new JButton("Entrar");
        panelBotones.add(botonVolver);
        panelBotones.add(botonEntrar);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centra los botones
        add(panelBotones, gbc);

        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loguearAlumno();
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrar(1);
            }
        });
    }
    private void loguearAlumno() {
        try {
            int idAlumno = Integer.parseInt(IdAlumno.getText());
            Alumno alumno = serviceAlumno.buscarAlumno(idAlumno);

            if (alumno != null) {
                panelManager.mostrarPanelDelAlumno(alumno);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un alumno con ese ID.", "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese solo números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar alumno: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }
}
