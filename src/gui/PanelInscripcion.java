package gui;
import Entidades.Alumno;
import Entidades.Curso;
import service.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelInscripcion extends JPanel {
    private PanelManager panelManager;
    private ServiceAlumno serviceAlumno;
    private ServiceCurso serviceCurso;
    private ServiceInscripcion serviceInscripcion;

    private JComboBox<Alumno> comboAlumnos;
    private JComboBox<Curso> comboCursos;
    private JButton botonInscribir;
    private JButton botonActualizar;

    public PanelInscripcion(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.serviceAlumno = new ServiceAlumno();
        this.serviceCurso = new ServiceCurso();
        this.serviceInscripcion = new ServiceInscripcion();
        armarPanel();
        cargarDatos();
    }

    private void armarPanel() {
        setLayout(new GridBagLayout()); // Usamos un layout centrado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        comboAlumnos = new JComboBox<>();
        comboCursos = new JComboBox<>();
        botonInscribir = new JButton("Inscribir Alumno");
        botonActualizar = new JButton("Actualizar");

        // Alumno
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Seleccionar Alumno:"), gbc);
        gbc.gridx = 1;
        add(comboAlumnos, gbc);

        // Curso
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Seleccionar Curso:"), gbc);
        gbc.gridx = 1;
        add(comboCursos, gbc);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonActualizar);
        panelBotones.add(botonInscribir);

        // Botón
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.anchor = GridBagConstraints.CENTER;

        add(panelBotones, gbc);

        botonInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscribir();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });
    }

    private void cargarDatos() {
        try {
            // Cargar Alumnos
            List<Alumno> alumnos = serviceAlumno.buscarTodosLosAlumnos();
            DefaultComboBoxModel<Alumno> modelAlumno = new DefaultComboBoxModel<>();
            for (Alumno a : alumnos) {
                modelAlumno.addElement(a);
            }
            comboAlumnos.setModel(modelAlumno);

            // Cargar Cursos
            List<Curso> cursos = serviceCurso.buscarTodosLosCursos();
            DefaultComboBoxModel<Curso> modelCurso = new DefaultComboBoxModel<>();
            for (Curso c : cursos) {
                modelCurso.addElement(c);
            }
            comboCursos.setModel(modelCurso);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar listas: " + e.getMessage());
        }
    }

    private void inscribir() {
        try {
            Alumno alumnoSel = (Alumno) comboAlumnos.getSelectedItem();
            Curso cursoSel = (Curso) comboCursos.getSelectedItem();

            if (alumnoSel == null || cursoSel == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno y un curso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            serviceInscripcion.inscribirAlumno(alumnoSel.getIdAlumno(), cursoSel.getIdCurso());
            JOptionPane.showMessageDialog(this, "Alumno inscripto correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al inscribir: " + e.getMessage(), "Error de Regla", JOptionPane.ERROR_MESSAGE);
        }
    }
}
