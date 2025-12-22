package gui;
import Entidades.Inscripcion;
import service.ServiceInscripcion;
import service.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel de visualización y gestión de Inscripciones
public class ReporteInscripciones extends JPanel{
    private PanelManager panelManager;
    private ServiceInscripcion serviceInscripcion;
    private JTable jTable;
    private DefaultTableModel contenidoTabla;
    private JScrollPane scrollPane;

    // Constructor del reporte el cual inicializa el servicio de inscripciones y construye la interfaz gráfica
    public ReporteInscripciones(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.serviceInscripcion = new ServiceInscripcion();
        armarTablaReporte();
    }

    // Configura los componentes visuales del reporte y también define las columnas de la tabla y asigna los eventos a los botones
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
        contenidoTabla.addColumn("ID Inscripción");
        contenidoTabla.addColumn("Alumno");
        contenidoTabla.addColumn("Curso");
        contenidoTabla.addColumn("Fecha");
        contenidoTabla.addColumn("Importe Pagado");

        JPanel panelBotones = new JPanel();
        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnDarDeBaja = new JButton("Dar de Baja Inscripción Seleccionada");

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnDarDeBaja);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarDatos());
        btnDarDeBaja.addActionListener(e -> darDeBaja());

        cargarDatos(); // Carga inicial
    }

    // Consulta al servicio por todas las inscripciones y actualiza la tabla
    private void cargarDatos() {
        contenidoTabla.setRowCount(0); // Limpia
        try {
            List<Inscripcion> inscripciones = serviceInscripcion.consultarTodas();
            for (Inscripcion insc : inscripciones) {
                Object[] fila = new Object[5];
                fila[0] = insc.getIdInscripcion();
                fila[1] = insc.getAlumno().getNombre() + " " + insc.getAlumno().getApellido(); // Se concatena nombre y apellido para mejor lectura
                fila[2] = insc.getCurso().getNombreCurso();
                fila[3] = insc.getFechaInscripcion().toString();
                fila[4] = insc.getImportePagado();
                contenidoTabla.addRow(fila);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar inscripciones: " + e.getMessage());
        }
    }

    // Lógica la cual elimina (cancela) una inscripción seleccionada
    private void darDeBaja() {
        int filaSeleccionada = jTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una inscripción de la tabla.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtenemos ID oculto en la primera columna
        int idInscripcion = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea dar de baja la inscripción ID: " + idInscripcion + "?",
                "Confirmar Baja",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                serviceInscripcion.cancelarInscripcion(idInscripcion);
                JOptionPane.showMessageDialog(this, "Se dió de baja la inscripción correctamente.");
                cargarDatos(); // Refresca la tabla para mostrar el cambio
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al dar de baja: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
