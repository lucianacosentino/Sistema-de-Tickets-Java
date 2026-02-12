package ui;

import service.TicketService;
import model.Ticket;
import model.Prioridad;
import model.EstadoTicket;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private TicketService service;
    private JTextPane areaTickets;

    public MainWindow(TicketService service) {
        this.service = service;

        setTitle("Sistema de Tickets");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    // Helper: Add a ticket with color coding for estado and style for prioridad
    private void agregarTicketColor(Ticket t) {
        StyledDocument doc = areaTickets.getStyledDocument();
        Style style = areaTickets.addStyle("ticketStyle", null);

        // Color by estado
        switch (t.getEstado()) {
            case ABIERTO -> StyleConstants.setForeground(style, new Color(0, 153, 0)); // verde
            case EN_PROGRESO -> StyleConstants.setForeground(style, Color.ORANGE);
            case CERRADO -> StyleConstants.setForeground(style, Color.RED);
        }

        // Style by prioridad
        switch (t.getPrioridad()) {
            case ALTA -> StyleConstants.setBold(style, true);
            case MEDIA -> StyleConstants.setItalic(style, true);
            default -> StyleConstants.setBold(style, false);
        }

        try {
            doc.insertString(doc.getLength(), t.toString() + "\n", style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper: Display list of tickets
    private void mostrarTicketsConColor(List<Ticket> tickets) {
        areaTickets.setText("");
        for (Ticket t : tickets) {
            agregarTicketColor(t);
        }
    }

    private void crearComponentes() {
        JPanel panel = new JPanel(new BorderLayout());

        areaTickets = new JTextPane();
        areaTickets.setEditable(false);

        // Panel de botones vertical
        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCrear = new JButton("Crear Ticket");
        JButton btnListar = new JButton("Listar Tickets");
        JButton btnBorrar = new JButton("Borrar Ticket");
        JButton btnFiltrarEstado = new JButton("Filtrar por estado");
        JButton btnFiltrarPrioridad = new JButton("Filtrar por prioridad");
        JButton btnCambiarEstado = new JButton("Cambiar estado");
        JButton btnMostrarTodos = new JButton("Mostrar todos");

        // Add spacing and buttons vertically
        for (JButton b : new JButton[]{btnCrear, btnListar, btnBorrar, btnFiltrarEstado, btnFiltrarPrioridad, btnCambiarEstado, btnMostrarTodos}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            botones.add(b);
            botones.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        panel.add(new JScrollPane(areaTickets), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.EAST); // buttons on right side

        add(panel);

        // Button actions
        btnCrear.addActionListener(e -> crearTicket());
        btnListar.addActionListener(e -> listarTickets());
        btnBorrar.addActionListener(e -> borrarTicket());
        btnFiltrarEstado.addActionListener(e -> filtrarPorEstado());
        btnFiltrarPrioridad.addActionListener(e -> filtrarPorPrioridad());
        btnCambiarEstado.addActionListener(e -> cambiarEstado());
        btnMostrarTodos.addActionListener(e -> listarTickets()); // reset filters
    }

    private void cambiarEstado() {
        String input = JOptionPane.showInputDialog(this, "ID del ticket:");
        if (input == null) return;

        int id;
        try {
            id = Integer.parseInt(input.trim());
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

        JOptionPane.showMessageDialog(this, cambiado ? "Estado actualizado ✅" : "No existe un ticket con ese ID");
        listarTickets();
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
                opciones[1]
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
        mostrarTicketsConColor(service.filtrarPorEstado(estado));
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
        mostrarTicketsConColor(service.filtrarPorPrioridad(prioridad));
    }

    private void listarTickets() {
        mostrarTicketsConColor(service.obtenerTickets());
    }

    private void borrarTicket() {
        String input = JOptionPane.showInputDialog(this, "ID del ticket a borrar:");
        if (input == null) return;

        try {
            int id = Integer.parseInt(input.trim());
            boolean borrado = service.borrarTicket(id);

            JOptionPane.showMessageDialog(this, borrado ? "Ticket borrado 🗑️" : "No existe ese ticket");
            listarTickets();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
}
