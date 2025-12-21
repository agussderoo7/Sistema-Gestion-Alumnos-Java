package gui;
import Entidades.Curso;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdmin extends JPanel{
    private PanelManager panelManager;
    private JTabbedPane pestanas; // Componente que maneja las pestañas

    // Los paneles que irán en las pestañas
    private FormularioAlumno panelFormularioAlumno;
    private FormularioCurso panelFormularioCurso;
    private ReporteAlumnos panelReporteAlumnos;
    private ReporteCurso panelReporteCursos;
    private PanelInscripcion panelInscripcion;
    private ReporteInscripciones panelReporteInscripciones;
    private FormularioProfesor panelFormularioProfesor;
    private FormularioAbono panelFormularioAbono;

    public PanelAdmin(PanelManager panelManager) {
        this.panelManager = panelManager;
        armarPanelAdmin();
    }

    private void armarPanelAdmin(){
        this.setLayout(new BorderLayout());

        panelFormularioAlumno = new FormularioAlumno(panelManager);
        panelReporteAlumnos = new ReporteAlumnos(panelManager);
        panelFormularioCurso = new FormularioCurso(panelManager);
        panelReporteCursos = new ReporteCurso(panelManager, this);
        panelInscripcion = new PanelInscripcion(panelManager);
        panelReporteInscripciones = new ReporteInscripciones(panelManager);
        panelFormularioProfesor = new FormularioProfesor(panelManager);
        panelFormularioAbono = new FormularioAbono(panelManager);

        pestanas = new JTabbedPane();
        pestanas.addTab("Inscribir Alumno", panelInscripcion);
        pestanas.addTab("Gestionar Inscripciones", panelReporteInscripciones);
        pestanas.addTab("Gestionar Alumnos", panelFormularioAlumno);
        pestanas.addTab("Reporte de Alumnos", panelReporteAlumnos);
        pestanas.addTab("Gestionar Profesores", panelFormularioProfesor);
        pestanas.addTab("Gestionar Abonos", panelFormularioAbono);
        pestanas.addTab("Agregar Cursos", panelFormularioCurso); // Es el índice 6
        pestanas.addTab("Gestionar Cursos", panelReporteCursos);
        pestanas.setPreferredSize(new Dimension(1000, 600)); // Obliga a las tablas internas a adaptarse a este espacio
        this.add(pestanas, BorderLayout.CENTER);

        JButton botonVolver = new JButton("Volver al Menú Principal");
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrar(1);
            }
        });
        this.add(botonVolver, BorderLayout.SOUTH);
    }

    public void irAPestanaModificarCurso(Curso curso) {
        panelFormularioCurso.cargarDatosParaModificar(curso);
        pestanas.setSelectedIndex(6); // Lleva a "Gestionar Cursos" para modificarlo
    }
}
