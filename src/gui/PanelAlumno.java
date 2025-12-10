package gui;
import Entidades.Alumno;
import Entidades.Inscripcion;
import service.ServiceInscripcion;
import service.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelAlumno extends JPanel {
    private PanelManager panelManager;
    private ServiceInscripcion serviceInscripcion; // Para buscar notas
    private Alumno alumnoLogueado;

    private JTable jTable;
    private DefaultTableModel contenidoTabla;
    private JScrollPane scrollPane;
    private JButton botonSalir;
    private JLabel labelBienvenida;

    public PanelAlumno(PanelManager panel, Alumno alumno) {
        this.panelManager = panel;
        this.serviceInscripcion = new ServiceInscripcion();
        this.alumnoLogueado = alumno;
        armarPanelLoginAlumno();
    }

    public void armarPanelLoginAlumno(){
        setLayout(new BorderLayout(10, 10));

        String textoBienvenida = "Bienvenido, " + alumnoLogueado.getNombre() + " " + alumnoLogueado.getApellido() + "!";
        labelBienvenida = new JLabel(textoBienvenida, SwingConstants.CENTER);
        labelBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(labelBienvenida, BorderLayout.NORTH);

        contenidoTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable = new JTable(contenidoTabla);
        scrollPane = new JScrollPane(jTable);

        contenidoTabla.addColumn("Curso");
        contenidoTabla.addColumn("Fecha de Inscripción");
        contenidoTabla.addColumn("Nota Final");
        contenidoTabla.addColumn("Estado de aprobación");

        add(scrollPane, BorderLayout.CENTER);

        botonSalir = new JButton("Cerrar Sesión");
        botonSalir.addActionListener(e -> panelManager.mostrar(1)); // Volver al Menú Principal

        JPanel panelBoton = new JPanel(); // Panel para que el botón no se estire
        panelBoton.add(botonSalir);
        add(panelBoton, BorderLayout.SOUTH);

        cargarNotas();
    }

    private void cargarNotas() {
        try {
            List<Inscripcion> inscripciones = serviceInscripcion.consultarPorAlumno(alumnoLogueado.getIdAlumno());

            if (inscripciones.isEmpty()) {
                return;
            }

            for (Inscripcion insc : inscripciones) {
                Object[] fila = new Object[4];

                fila[0] = insc.getCurso().getNombreCurso();
                fila[1] = insc.getFechaInscripcion().toString();

                String estado = "Cursando";
                int notaFinal = insc.getNotaFinal();

                if (notaFinal == 0) {
                    fila[2] = "-"; // Todavía no tiene nota
                } else {
                    fila[2] = notaFinal;
                    if (notaFinal >= insc.getCurso().getNotaAprobacion()) {
                        estado = "Aprobado";
                    } else {
                        estado = "Desaprobado";
                    }
                }

                fila[3] = estado;
                contenidoTabla.addRow(fila);
            }

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tus notas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
