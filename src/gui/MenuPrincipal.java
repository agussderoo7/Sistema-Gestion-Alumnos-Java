package gui;
import service.ServiceAlumno;
import service.ServiceProfesor;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuPrincipal extends JPanel {
    PanelManager panel;
    private JButton botonAdmin;
    private JButton botonProfesor;
    private JButton botonAlumno;

    public MenuPrincipal(PanelManager panel){
        this.panel = panel;
        armarMenuPrincipal();
    }

    public void armarMenuPrincipal(){
        this.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        botonAdmin = new JButton("Entrar como Administrador");
        botonProfesor = new JButton("Entrar como Profesor");
        botonAlumno = new JButton("Entrar como Alumno");

        botonAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonProfesor.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonAlumno.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotones.add(Box.createVerticalStrut(50)); // Un espacio en blanco
        panelBotones.add(botonAdmin);
        panelBotones.add(Box.createVerticalStrut(20)); // Un espacio entre botones
        panelBotones.add(botonProfesor);
        panelBotones.add(Box.createVerticalStrut(20)); // Un espacio entre botones
        panelBotones.add(botonAlumno);

        this.add(panelBotones, BorderLayout.CENTER);

        botonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.mostrar(4);
            }
        });

        botonProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.mostrar(5);
            }
        });

        botonAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.mostrar(6);
            }
        });
    }
}