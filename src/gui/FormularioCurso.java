package gui;
import Entidades.Curso;
import Entidades.Profesor;
import service.ServiceCurso;
import service.ServiceProfesor;
import service.ServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Date;

public class FormularioCurso extends JPanel {
    private ServiceCurso serviceCurso;
    private ServiceProfesor serviceProfesor;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCupo;
    private JTextField txtNotaAprobacion;
    private JTextField txtCantParciales;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JTextField txtPrecioPromo;
    private JComboBox<Profesor> comboProfesor;
    private JButton botonGrabar;
    private JButton botonRefrescar;
    private Integer idCursoModificando = null;

    public FormularioCurso(PanelManager panel) {
        this.serviceCurso = new ServiceCurso();
        this.serviceProfesor = new ServiceProfesor();
        armarFormulario();
    }

    private void armarFormulario() {
        // GridBagLayout para control total
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre componentes (Arriba, Izquierda, Abajo, Derecha)
        gbc.anchor = GridBagConstraints.WEST; // Alinear textos a la izquierda
        gbc.weighty = 1.0; // Cuando maximiza la pantalla se ajusta
        gbc.ipady = 15; // Le da más presencia

        txtNombre = new JTextField(20);
        txtPrecio = new JTextField(10);
        txtCupo = new JTextField(5);
        txtNotaAprobacion = new JTextField(5);
        txtCantParciales = new JTextField(5);
        txtFechaDesde = new JTextField("ej: 2025-01-30");
        txtFechaHasta = new JTextField("ej: 2025-03-15");
        txtPrecioPromo = new JTextField(10);
        comboProfesor = new JComboBox<>();
        botonGrabar = new JButton("Grabar Curso");
        botonRefrescar = new JButton("Refrescar Profesores");

        // Cargo profesores en el JComboBox
        cargarProfesores();

        // (Fila 0) Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0; // El Label NO se estira
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nombre del Curso:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ocupa el ancho
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtNombre, gbc);

        // Precio
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0; // Resetear peso para el label
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Precio ($):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Estirar campo
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtPrecio, gbc);

        // Cupo
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Cupo:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtCupo, gbc);

        // Nota Aprobación
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nota Aprobación:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtNotaAprobacion, gbc);

        // Cantidad Parciales
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Cant. Parciales:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtCantParciales, gbc);

        // Fecha Desde
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Promo Desde (AAAA-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtFechaDesde, gbc);

        // Fecha Hasta
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Promo Hasta (AAAA-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtFechaHasta, gbc);

        // Precio Promoción
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Precio Promoción ($):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(txtPrecioPromo, gbc);

        // Profesor
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Profesor Asignado:"), gbc);

        // Panel para combo y botón
        JPanel panelProfe = new JPanel(new BorderLayout(5, 0));
        panelProfe.setBackground(Color.white);
        panelProfe.add(comboProfesor, BorderLayout.CENTER);
        panelProfe.add(botonRefrescar, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelProfe, gbc);

        // Grabar
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2; // Ocupa las 2 columnas
        gbc.weightx = 1.0; // Importante para que se centre en el ancho
        gbc.fill = GridBagConstraints.NONE; // NO estirar el botón
        gbc.anchor = GridBagConstraints.CENTER; // Centrarlo
        gbc.insets = new Insets(20, 5, 5, 5); // Margen extra arriba

        botonGrabar.setPreferredSize(new Dimension(200, 40));
        add(botonGrabar, gbc);

        // Botones
        botonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grabarCurso();
            }
        });

        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProfesores();
                JOptionPane.showMessageDialog(null, "Lista de profesores actualizada");
            }
        });
    }

    private void cargarProfesores() {
        try {
            List<Profesor> profesores = serviceProfesor.buscarTodosLosProfesores();

            DefaultComboBoxModel<Profesor> model = new DefaultComboBoxModel<>();
            if (profesores.isEmpty()) {
                // Si no hay profesores, no se puede crear un curso
                JOptionPane.showMessageDialog(this, "Error: No hay profesores cargados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                botonGrabar.setEnabled(false); // Deshabilita el botón
            } else {
                for (Profesor p : profesores) {
                    model.addElement(p);
                }
            }
            comboProfesor.setModel(model);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar profesores: " + e.getMessage());
        }
    }

    private void grabarCurso() {
        try {
            String nombre = txtNombre.getText();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del curso es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            float precio = Float.parseFloat(txtPrecio.getText());
            int cupo = Integer.parseInt(txtCupo.getText());
            int notaAprobacion = Integer.parseInt(txtNotaAprobacion.getText());
            int cantParciales = Integer.parseInt(txtCantParciales.getText());
            Date fechaDesde = Date.valueOf(txtFechaDesde.getText());
            Date fechaHasta = Date.valueOf(txtFechaHasta.getText());
            float precioPromo = Float.parseFloat(txtPrecioPromo.getText());

            Profesor profeSeleccionado = (Profesor) comboProfesor.getSelectedItem();
            if (profeSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un profesor.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idCursoModificando == null) {
                serviceCurso.crearCurso(
                        nombre, precio, cupo, notaAprobacion,
                        cantParciales, fechaDesde, fechaHasta,
                        precioPromo, profeSeleccionado.getIdProfesor()
                );
                JOptionPane.showMessageDialog(this, "¡Curso creado con éxito!");
            } else {
                // Modo modificar
                Curso cursoModificado = new Curso();
                cursoModificado.setIdCurso(idCursoModificando);
                cursoModificado.setNombreCurso(nombre);
                cursoModificado.setPrecioCurso(precio);
                cursoModificado.setCupo(cupo);
                cursoModificado.setNotaAprobacion(notaAprobacion);
                cursoModificado.setCantidadParciales(cantParciales);
                cursoModificado.setFechaPromocionDesde(fechaDesde);
                cursoModificado.setFechaPromocionHasta(fechaHasta);
                cursoModificado.setPrecioPromocion(precioPromo);
                cursoModificado.setProfesor(profeSeleccionado);

                serviceCurso.modificarCurso(cursoModificado);
                JOptionPane.showMessageDialog(this, "¡Curso (ID: " + idCursoModificando + ") modificado con éxito!");
            }
            limpiarFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato: Precio, Cupo, Notas y Parciales deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato en Fecha: Use el formato AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al grabar el curso: " + ex.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        idCursoModificando = null;
        botonGrabar.setText("Grabar (Nuevo)");
    }

    public void cargarDatosParaModificar(Curso curso) {
        idCursoModificando = curso.getIdCurso();
        botonGrabar.setText("Guardar Cambios (ID: " + idCursoModificando + ")");

        txtNombre.setText(curso.getNombreCurso());
        txtPrecio.setText(String.valueOf(curso.getPrecioCurso()));
        txtCupo.setText(String.valueOf(curso.getCupo()));
        txtNotaAprobacion.setText(String.valueOf(curso.getNotaAprobacion()));
        txtCantParciales.setText(String.valueOf(curso.getCantidadParciales()));
        txtFechaDesde.setText(curso.getFechaPromocionDesde().toString());
        txtFechaHasta.setText(curso.getFechaPromocionHasta().toString());
        txtPrecioPromo.setText(String.valueOf(curso.getPrecioPromocion()));

        DefaultComboBoxModel<Profesor> model = (DefaultComboBoxModel<Profesor>) comboProfesor.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).getIdProfesor() == curso.getProfesor().getIdProfesor()) {
                comboProfesor.setSelectedIndex(i);
            }
        }
    }
}