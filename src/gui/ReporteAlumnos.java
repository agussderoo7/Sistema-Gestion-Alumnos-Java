package gui;
import Entidades.Alumno;
import service.ServiceAlumno;
import service.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReporteAlumnos extends JPanel {
    private PanelManager panelManager;
    private JTable jTable;
    private DefaultTableModel contenidoTabla;
    private JScrollPane scrollPane;
    private ServiceAlumno serviceAlumno;

    public ReporteAlumnos(PanelManager panelManager){
        this.panelManager = panelManager;
        this.serviceAlumno = new ServiceAlumno();
        armarTablaReporte();
    }

    public void armarTablaReporte() {
        setLayout(new BorderLayout());

        // Para que las tablas no sean editables
        contenidoTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        jTable = new JTable(contenidoTabla);
        scrollPane = new JScrollPane(jTable);
        scrollPane.setViewportView(jTable);

        contenidoTabla.addColumn("ID");
        contenidoTabla.addColumn("Nombre");
        contenidoTabla.addColumn("Apellido");
        contenidoTabla.addColumn("Email");
        contenidoTabla.addColumn("Limite de Cursos");
        contenidoTabla.addColumn("Abono");

        JPanel panelBotones = new JPanel();
        JButton botonRefrescar = new JButton("Refrescar");
        JButton botonEliminar = new JButton("Eliminar alumno seleccionado");

        panelBotones.add(botonRefrescar);
        panelBotones.add(botonEliminar);

        // Pongo la tabla al medio y los botones abajo
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> cargarDatos());
        botonEliminar.addActionListener(e -> eliminarAlumnoSeleccionado());

        cargarDatos();
    }

    // Separo la lógica para poder refrescar
    private void cargarDatos() {
        contenidoTabla.setRowCount(0); // Limpia la tabla
        try {
            List<Alumno> alumnos = serviceAlumno.buscarTodosLosAlumnos();
            for(Alumno alumno : alumnos) {
                Object [] fila= new Object[6];
                fila[0]=alumno.getIdAlumno();
                fila[1]=alumno.getNombre();
                fila[2]=alumno.getApellido();
                fila[3]=alumno.getEmail();
                fila[4]=alumno.getLimiteCursos();
                if(alumno.getAbono() != null){
                    fila[5]=alumno.getAbono().getNombre();
                } else {
                    fila[5]="Ninguno";
                }
                contenidoTabla.addRow(fila);
            }
        } catch ( ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void eliminarAlumnoSeleccionado(){
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = jTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno de la tabla para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener ID (está en la columna 0)
        int idAlumno = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) contenidoTabla.getValueAt(filaSeleccionada, 1);
        String apellido = (String) contenidoTabla.getValueAt(filaSeleccionada, 2);

        // Pedir confirmación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "Seguro que desea eliminar al alumno " + nombre + " " + apellido + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                serviceAlumno.eliminarAlumno(idAlumno);
                JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente.");

                // Refrescar la tabla
                cargarDatos();
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}