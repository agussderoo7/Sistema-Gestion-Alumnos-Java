package gui;

// Importa todos los servicios y entidades que necesita
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
        this.setLayout(new GridLayout(10, 2, 10, 10));
        this.setBackground(Color.white);

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

        add(new JLabel("Nombre del Curso:"));
        add(txtNombre);
        add(new JLabel("Precio: ($)"));
        add(txtPrecio);
        add(new JLabel("Cupo:"));
        add(txtCupo);
        add(new JLabel("Nota Aprobación: (ej: 6)"));
        add(txtNotaAprobacion);
        add(new JLabel("Cantidad de Parciales:"));
        add(txtCantParciales);
        add(new JLabel("Fecha Promo Desde (YYYY-MM-DD):"));
        add(txtFechaDesde);
        add(new JLabel("Fecha Promo Hasta (YYYY-MM-DD):"));
        add(txtFechaHasta);
        add(new JLabel("Precio Promoción: ($)"));
        add(txtPrecioPromo);
        add(new JLabel("Profesor Asignado:"));
        add(comboProfesor);
        add(new JLabel("")); // Espacio

        add(new JLabel("Profesor:"));

        JPanel panelProfesor = new JPanel(new BorderLayout());
        panelProfesor.add(comboProfesor, BorderLayout.CENTER);
        panelProfesor.add(botonRefrescar, BorderLayout.EAST);
        add(panelProfesor);

        add(new JLabel(""));
        add(botonGrabar);

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
                // Si no hay profes, no se puede crear un curso
                JOptionPane.showMessageDialog(this, "Error: No hay profesores cargados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                botonGrabar.setEnabled(false); // Esta parte deshabilita el botón
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
            int notaAprob = Integer.parseInt(txtNotaAprobacion.getText());
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
                        nombre, precio, cupo, notaAprob,
                        cantParciales, fechaDesde, fechaHasta,
                        precioPromo, profeSeleccionado.getIdProfesor()
                );
                JOptionPane.showMessageDialog(this, "¡Curso creado con éxito!");
            } else {
                // --- MODO MODIFICAR ---
                Curso cursoModificado = new Curso();
                cursoModificado.setIdCurso(idCursoModificando);
                cursoModificado.setNombreCurso(nombre);
                cursoModificado.setPrecioCurso(precio);
                cursoModificado.setCupo(cupo);
                cursoModificado.setNotaAprobacion(notaAprob);
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