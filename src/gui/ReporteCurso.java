package gui;
import Entidades.Curso;
import service.ServiceCurso;
import service.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel de visualización y gestión de los cursos
public class ReporteCurso extends JPanel{
    private PanelManager panelManager;
    private ServiceCurso serviceCurso;

    // Referencia necesaria para poder cambiar de pestaña al modificar
    private PanelAdmin panelAdmin;

    private JTable jTable;
    private DefaultTableModel contenidoTabla;
    private JScrollPane scrollPane;

    // Constructor del panel
    public ReporteCurso(PanelManager panelManager, PanelAdmin panelAdmin) {
        this.panelManager = panelManager;
        this.panelAdmin = panelAdmin;
        this.serviceCurso = new ServiceCurso();
        armarTablaReporte();
    }

    // Arma la interfaz gráfica del reporte
    public void armarTablaReporte() {
        setLayout(new BorderLayout());

        contenidoTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        jTable = new JTable(contenidoTabla);
        scrollPane = new JScrollPane(jTable);

        // Definición de columnas
        contenidoTabla.addColumn("ID");
        contenidoTabla.addColumn("Nombre del Curso");
        contenidoTabla.addColumn("Profesor");
        contenidoTabla.addColumn("Precio");
        contenidoTabla.addColumn("Cupo");
        contenidoTabla.addColumn("Nota Aprobación");

        JPanel panelBotones = new JPanel();
        JButton botonRefrescar = new JButton("Refrescar");
        JButton botonModificar = new JButton("Modificar Curso Seleccionado");
        JButton botonEliminar = new JButton("Eliminar Curso Seleccionado");
        JButton botonReporteDetallado = new JButton("Reporte Detallado");

        panelBotones.add(botonRefrescar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonReporteDetallado);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> cargarDatos());
        botonEliminar.addActionListener(e -> eliminarCurso());
        botonModificar.addActionListener(e -> modificarCurso());
        botonReporteDetallado.addActionListener(e -> verReporteDetallado());

        cargarDatos();
    }

    // Obtiene la lista completa de cursos desde el servicio y llena la tabla
    private void cargarDatos() {
        contenidoTabla.setRowCount(0); // Limpia
        try {
            List<Curso> cursos = serviceCurso.buscarTodosLosCursos();
            for (Curso curso : cursos) {
                Object[] fila = new Object[6];
                fila[0] = curso.getIdCurso();
                fila[1] = curso.getNombreCurso();
                fila[2] = curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellido();
                fila[3] = curso.getPrecioCurso();
                fila[4] = curso.getCupo();
                fila[5] = curso.getNotaAprobacion();
                contenidoTabla.addRow(fila);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cursos: " + e.getMessage());
        }
    }

    // Lógica para eliminar un curso
    private void eliminarCurso() {
        int filaSeleccionada = jTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso de la tabla para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCurso = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);

        int confirmar = JOptionPane.showConfirmDialog(this,
                "Está seguro que desea eliminar el curso con ID: " + idCurso + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                serviceCurso.eliminarCurso(idCurso);
                JOptionPane.showMessageDialog(this, "¡Curso eliminado con éxito!");
                cargarDatos(); // Refresca la tabla
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el curso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Prepara la interfaz para modificar un curso existente
    private void modificarCurso() {
        int filaSeleccionada = jTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un curso de la tabla para modificar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCurso = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);

        try {
            Curso cursoModificar = serviceCurso.buscarCurso(idCurso);
            panelAdmin.irAPestanaModificarCurso(cursoModificar);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del curso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Genera y visualiza un reporte estadístico detallado del curso seleccionado
    private void verReporteDetallado(){
        int filaSeleccionada = jTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso para ver su reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCurso = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);
        try {
            // Llamamos al metodo de servicio que ya hice y calcula
            String reporte = serviceCurso.generarReporteCurso(idCurso);

            // Lo mostramos en un mensaje
            JOptionPane.showMessageDialog(this, reporte, "Estadísticas del Curso", JOptionPane.INFORMATION_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
        }
    }
}
