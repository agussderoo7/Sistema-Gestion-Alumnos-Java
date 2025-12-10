package gui;
import Entidades.Curso;
import Entidades.Inscripcion;
import Entidades.Profesor;
import service.ServiceCurso;
import service.ServiceInscripcion;
import service.ServiceNotaParcial;
import service.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelProfesor extends JPanel{
    private PanelManager panelManager;
    private ServiceCurso serviceCurso;
    private ServiceInscripcion serviceInscripcion;
    private ServiceNotaParcial serviceNotaParcial;

    private JTable jTableAlumnos;
    private DefaultTableModel contenidoTabla;
    private JScrollPane scrollPane;
    private JTextField NotaParcial;
    private JButton botonCargarNota;
    private JButton botonVolver;
    private JComboBox<Curso> comboCursos;

    public PanelProfesor(PanelManager panel){
        this.panelManager = panel;
        this.serviceCurso = new ServiceCurso();
        this.serviceInscripcion = new ServiceInscripcion();
        this.serviceNotaParcial = new ServiceNotaParcial();

        armarPanel();
        cargarCursos();
    }

    public void armarPanel(){
        this.setLayout(new BorderLayout(10, 10));

        // Elegir un curso
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Seleccione un Curso:"));
        comboCursos = new JComboBox<>();
        panelSuperior.add(comboCursos);
        comboCursos.addActionListener(e -> cargarAlumnosDelCurso());
        this.add(panelSuperior, BorderLayout.NORTH);

        contenidoTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Esto hace que NINGUNA celda sea editable
                return false;
            }
        };

        this.add(panelSuperior, BorderLayout.NORTH);

        // Tabla de alumnos
        jTableAlumnos = new JTable(contenidoTabla);
        scrollPane = new JScrollPane(jTableAlumnos);

        contenidoTabla.addColumn("ID Inscripción");
        contenidoTabla.addColumn("Alumno");
        contenidoTabla.addColumn("Nota Final");
        contenidoTabla.addColumn("Estado");

        this.add(scrollPane, BorderLayout.CENTER);

        // Cargar nota y volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelInferior.add(new JLabel("Nota Parcial:"));
        NotaParcial = new JTextField(5);
        panelInferior.add(NotaParcial);

        botonCargarNota = new JButton("Cargar Nota");
        panelInferior.add(botonCargarNota);

        botonVolver = new JButton("Volver al Menú");
        panelInferior.add(botonVolver);

        this.add(panelInferior, BorderLayout.SOUTH);

        botonCargarNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNotaParcial();
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrar(1);
            }
        });
    }

    private void cargarCursos() {
        try {
            List<Curso> cursos = serviceCurso.buscarTodosLosCursos();

            DefaultComboBoxModel<Curso> model = new DefaultComboBoxModel<>();
            for (Curso c : cursos) {
                model.addElement(c);
            }
            comboCursos.setModel(model);

            // Llama a cargarAlumnos para el primer curso de la lista
            cargarAlumnosDelCurso();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos: " + e.getMessage());
        }
    }

    private void cargarAlumnosDelCurso(){
        // Limpia la tabla
        contenidoTabla.setRowCount(0);

        // Obtiene el curso seleccionado
        Curso cursoSeleccionado = (Curso) comboCursos.getSelectedItem();
        if (cursoSeleccionado == null) {
            return; // No hay cursos, no hace nada
        }

        try {
            List<Inscripcion> inscripciones = serviceInscripcion.consultarPorCurso(cursoSeleccionado.getIdCurso());

            for (Inscripcion insc : inscripciones) {
                Object[] fila = new Object[4];
                fila[0] = insc.getIdInscripcion();
                fila[1] = insc.getAlumno().getNombre() + " " + insc.getAlumno().getApellido();

                int notaFinal = insc.getNotaFinal();
                String estado = "Cursando";

                if (notaFinal == 0) {
                    fila[2] = "-";
                } else {
                    fila[2] = notaFinal;
                    if (notaFinal >= cursoSeleccionado.getNotaAprobacion()) {
                        estado = "Aprobado";
                    } else {
                        estado = "Desaprobado";
                    }
                }
                fila[3] = estado;
                contenidoTabla.addRow(fila);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar alumnos: " + e.getMessage());
        }
    }

    private void cargarNotaParcial(){
        try {
            int filaSeleccionada = jTableAlumnos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un alumno de la tabla.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int nota = Integer.parseInt(NotaParcial.getText());
            if (nota < 1 || nota > 10) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 1 y 10.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener el ID de la Inscripción que está en la columna 0
            int idInscripcion = (int) contenidoTabla.getValueAt(filaSeleccionada, 0);
            serviceNotaParcial.agregarNotaParcial(idInscripcion, nota);
            JOptionPane.showMessageDialog(this, "Nota parcial cargada correctamente");
            NotaParcial.setText(""); // Limpiar el campo

            cargarAlumnosDelCurso();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La nota debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la nota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizar(){
        cargarCursos();
        contenidoTabla.setRowCount(0);
    }
}
