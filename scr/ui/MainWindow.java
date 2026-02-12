package ui;

import service.TicketService;
import model.Ticket;
import model.Prioridad;
import model.EstadoTicket;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private TicketService service;
    private JTextArea areaTickets;

    public MainWindow(TicketService service) {
        this.service = service;

        setTitle("Sistema de Tickets");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    private void crearComponentes() {
        JPanel panel = new JPanel(new BorderLayout());

        areaTickets = new JTextArea();
        areaTickets.setEditable(false);

        JButton btnCrear = new JButton("Crear Ticket");
        JButton btnListar = new JButton("Listar Tickets");
        JButton btnBorrar = new JButton("Borrar Ticket");

        JPanel botones = new JPanel();
        botones.add(btnCrear);
        botones.add(btnListar);
        botones.add(btnBorrar);

        panel.add(new JScrollPane(areaTickets), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        add(panel);

        btnCrear.addActionListener(e -> crearTicket());
        btnListar.addActionListener(e -> listarTickets());
        btnBorrar.addActionListener(e -> borrarTicket());
        JButton btnFiltrarEstado = new JButton("Filtrar por estado");
        JButton btnFiltrarPrioridad = new JButton("Filtrar por prioridad");

        botones.add(btnFiltrarEstado);
        botones.add(btnFiltrarPrioridad);
        btnFiltrarEstado.addActionListener(e -> filtrarPorEstado());
        btnFiltrarPrioridad.addActionListener(e -> filtrarPorPrioridad());

        JButton btnCambiarEstado = new JButton("Cambiar estado");
        botones.add(btnCambiarEstado);

        btnCambiarEstado.addActionListener(e -> cambiarEstado());


    }
    private void cambiarEstado() {
        String input = JOptionPane.showInputDialog(this, "ID del ticket:");

        if (input == null) return;

        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
            return;
        }

        String[] opciones = {"ABIERTO", "EN_PROGRESO", "CERRADO"};

        String estadoStr = (String) JOptionPane.showInputDialog(
                this,
                "Nuevo estado:",
                "Cambiar estado",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (estadoStr == null) return;

        EstadoTicket estado = EstadoTicket.valueOf(estadoStr);

        boolean cambiado = service.cambiarEstado(id, estado);

        if (cambiado) {
            JOptionPane.showMessageDialog(this, "Estado actualizado ✅");
            listarTickets();
        } else {
            JOptionPane.showMessageDialog(this, "No existe un ticket con ese ID");
        }
    }

    private void crearTicket() {
        String titulo = JOptionPane.showInputDialog(this, "Título del ticket:");
        if (titulo == null || titulo.isEmpty()) return;

        String descripcion = JOptionPane.showInputDialog(this, "Descripción del ticket:");
        if (descripcion == null || descripcion.isEmpty()) return;

        String[] opciones = {"BAJA", "MEDIA", "ALTA"};

        String prioridadStr = (String) JOptionPane.showInputDialog(
                this,
                "Seleccioná la prioridad:",
                "Prioridad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1] // MEDIA por defecto
        );

        if (prioridadStr == null) return;

        Prioridad prioridad = Prioridad.valueOf(prioridadStr);

        service.crearTicket(titulo, descripcion, prioridad);

        JOptionPane.showMessageDialog(this, "Ticket creado ✅");
        listarTickets();
    }
    private void filtrarPorEstado() {
        String[] opciones = {"ABIERTO", "EN_PROGRESO", "CERRADO"};

        String estadoStr = (String) JOptionPane.showInputDialog(
                this,
                "Seleccioná el estado:",
                "Filtrar por estado",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (estadoStr == null) return;

        EstadoTicket estado = EstadoTicket.valueOf(estadoStr);

        areaTickets.setText("");
        for (Ticket t : service.filtrarPorEstado(estado)) {
            areaTickets.append(t.toString() + "\n");
        }
    }
    private void filtrarPorPrioridad() {
        String[] opciones = {"BAJA", "MEDIA", "ALTA"};

        String prioridadStr = (String) JOptionPane.showInputDialog(
                this,
                "Seleccioná la prioridad:",
                "Filtrar por prioridad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );

        if (prioridadStr == null) return;

        Prioridad prioridad = Prioridad.valueOf(prioridadStr);

        areaTickets.setText("");
        for (Ticket t : service.filtrarPorPrioridad(prioridad)) {
            areaTickets.append(t.toString() + "\n");
        }
    }


    private void listarTickets() {
        areaTickets.setText("");
        for (Ticket t : service.obtenerTickets()) {
            areaTickets.append(t.toString() + "\n");
        }
    }

    private void borrarTicket() {
        String input = JOptionPane.showInputDialog(this, "ID del ticket a borrar:");

        try {
            int id = Integer.parseInt(input);
            boolean borrado = service.borrarTicket(id);

            if (borrado) {
                JOptionPane.showMessageDialog(this, "Ticket borrado 🗑️");
            } else {
                JOptionPane.showMessageDialog(this, "No existe ese ticket");
            }

            listarTickets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
}
