// gestiona/administra las pantallas que quiero mostrar
// en algún momento intercambia los paneles
package gui;
import Entidades.Alumno;
import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private FormularioAlumno formularioAlumno;
    private ReporteAlumnos reporteAlumnos;
    private MenuPrincipal menuPrincipal;
    private PanelAlumno panelAlumno;
    private PanelProfesor panelProfesor;
    private PanelAdmin panelAdmin;
    private PanelLoginAlumno panelLoginAlumno;

    JFrame jFrame;

    public PanelManager(int tipo) {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(tipo==1){
            menuPrincipal = new MenuPrincipal(this);
        }
        mostrar(menuPrincipal);
    }

    public void mostrar(JPanel panel){
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(BorderLayout.CENTER, panel); // que el panel lo ponga en un punto cardinal, en este caso, en el centro
        jFrame.getContentPane().validate();
        jFrame.getContentPane().repaint();
        jFrame.show();
        jFrame.pack();
    }

    public void mostrar(int codigoPantalla) {
        switch (codigoPantalla) {
            case 1:
                if (menuPrincipal == null) {
                    menuPrincipal = new MenuPrincipal(this);
                }
                mostrar(menuPrincipal);
                break;
            case 2:
                if (formularioAlumno == null) {
                    formularioAlumno = new FormularioAlumno(this);
                }
                mostrar(formularioAlumno);
                break;
            case 3:
                reporteAlumnos = new ReporteAlumnos(this);
                mostrar(reporteAlumnos);
                break;
            case 4:
                if (panelAdmin == null) {
                    panelAdmin = new PanelAdmin(this);
                }
                mostrar(panelAdmin);
                break;
            case 5:
                if (panelProfesor == null) {
                    panelProfesor = new PanelProfesor(this);
                }
                panelProfesor.actualizar();
                mostrar(panelProfesor);
                break;
            case 6:
                if (panelLoginAlumno == null) {
                    panelLoginAlumno = new PanelLoginAlumno(this);
                }
                mostrar(panelLoginAlumno);
                break;
        }
    }

    // Creamos un panel nuevo cada vez para el alumno específico
    public void mostrarPanelDelAlumno(Alumno alumnoLogueado) {
        PanelAlumno panelAlumno = new PanelAlumno(this, alumnoLogueado);
        mostrar(panelAlumno);
    }
}