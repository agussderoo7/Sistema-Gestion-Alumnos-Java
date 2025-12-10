import gui.PanelManager;
import service.*;
import Entidades.*;
import java.util.*;
import java.sql.Date;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        ServiceAlumno serviceAlumno = new ServiceAlumno();
        ServiceAbono serviceAbono = new ServiceAbono();
        ServiceProfesor serviceProfesor = new ServiceProfesor();
        ServiceCurso serviceCurso = new ServiceCurso();
        ServiceInscripcion serviceInscripcion = new ServiceInscripcion();
        ServiceNotaParcial serviceNotaParcial = new ServiceNotaParcial();

        try {
            // (Bonus) Creamos un Abono "Gratis"
            serviceAbono.crearAbono("Abono 100% (Beca)", 0, 100);
            serviceProfesor.crearProfesor("Carlos", "Perez", "carlosperez@gmail.com");

            // (Usamos Date.valueOf("YYYY-MM-DD") para las fechas de promo)
            Date promoDesde = Date.valueOf("2025-01-01");
            Date promoHasta = Date.valueOf("2026-12-31");

            // Creamos un curso con 3 parciales, nota de aprobación 6 y cupo para 2 alumnos
            serviceCurso.crearCurso("Laboratorio 1", 5000, 2, 6, 3, promoDesde, promoHasta, 4000, 1);

            // Alumno 1 (Agustín) tiene el Abono 1 (el gratis) y límite de 3 cursos
            serviceAlumno.crearAlumno("Agustín", "De Roo", "agus@mail.com", 3, 1);

            // Alumno 2 (Santiago) no tiene abono (idAbono = 0) y límite de 3 cursos
            serviceAlumno.crearAlumno("Santiago", "Jordan", "santi@mail.com", 3, 0);

            // Inscribimos a Agustín (ID 1) al Curso (ID 1)
            // Debería aplicar el ABONO (costo 0)
            serviceInscripcion.inscribirAlumno(1, 1);
            System.out.println("Agustín (con abono) inscripto.");

            // Debería aplicar el PRECIO DE PROMOCIÓN ($4000)
            serviceInscripcion.inscribirAlumno(2, 1);
            System.out.println("Santiago (sin abono) inscripto.");

            // Cupo
            System.out.println("\nIntentando inscribir 3er alumno (debería fallar por limite de cupo):");
            try {
                serviceAlumno.crearAlumno("Lucas", "Lopez", "lucaslopez15@gmail.com", 3, 0);
                serviceInscripcion.inscribirAlumno(3, 1);
            } catch (ServiceException e) {
                System.out.println("Error: " + e.getMessage()); // Debería mostrar que el curso ya no tiene cupo disponible
            }

            // El curso (ID 1) requería 3 parciales con nota 6 para aprobar.
            serviceNotaParcial.agregarNotaParcial(2, 8);
            serviceNotaParcial.agregarNotaParcial(2, 7);
            // Al agregar esta tercera nota, el ServiceNotaParcial debería
            // calcular y guardar la nota final (Promedio: 8)
            serviceNotaParcial.agregarNotaParcial(2, 9);

            String reporte = serviceCurso.generarReporteCurso(1);
            System.out.println(reporte);

        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }
}