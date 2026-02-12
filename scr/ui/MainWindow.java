package ui;

import service.TicketService;
import model.Ticket;
import model.Prioridad;

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
