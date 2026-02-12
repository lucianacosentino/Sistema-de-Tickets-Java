package ui;

import service.TicketService;
import model.Ticket;
import model.EstadoTicket;
import model.Prioridad;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {

    private TicketService service;
    private JTable tableTickets;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<EstadoTicket> filterEstado;
    private JComboBox<Prioridad> filterPrioridad;

    public MainWindow(TicketService service) {
        this.service = service;

        setTitle("Sistema de Tickets");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
        listarTickets();
    }

    private void crearComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // --- Top panel: search + filters
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        searchField.setToolTipText("Buscar por título o descripción");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });

        filterEstado = new JComboBox<>();
        filterEstado.addItem(null); // show all
        for (EstadoTicket estado : EstadoTicket.values()) filterEstado.addItem(estado);
        filterEstado.addActionListener(e -> aplicarFiltros());

        filterPrioridad = new JComboBox<>();
        filterPrioridad.addItem(null); // show all
        for (Prioridad p : Prioridad.values()) filterPrioridad.addItem(p);
        filterPrioridad.addActionListener(e -> aplicarFiltros());

        topPanel.add(new JLabel("Buscar:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Estado:"));
        topPanel.add(filterEstado);
        topPanel.add(new JLabel("Prioridad:"));
        topPanel.add(filterPrioridad);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- Table
        String[] columnNames = {"ID", "Título", "Prioridad", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent editing directly
            }
        };
        tableTickets = new JTable(tableModel);
        tableTickets.setFillsViewportHeight(true);
        tableTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderer for colors & styles
        tableTickets.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                EstadoTicket estado = (EstadoTicket) table.getValueAt(row, 3);
                Prioridad prioridad = (Prioridad) table.getValueAt(row, 2);

                if (estado == EstadoTicket.ABIERTO) c.setForeground(new Color(0, 153, 0));
                else if (estado == EstadoTicket.EN_PROGRESO) c.setForeground(Color.ORANGE);
                else if (estado == EstadoTicket.CERRADO) c.setForeground(Color.RED);

                Font font = c.getFont();
                if (prioridad == Prioridad.ALTA) c.setFont(font.deriveFont(Font.BOLD));
                else if (prioridad == Prioridad.MEDIA) c.setFont(font.deriveFont(Font.ITALIC));
                else c.setFont(font.deriveFont(Font.PLAIN));

                if (isSelected) c.setBackground(new Color(220, 220, 255));
                else c.setBackground(Color.WHITE);

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableTickets);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Buttons panel
        JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnCrear = new JButton("Crear Ticket");
        JButton btnBorrar = new JButton("Borrar Ticket");
        JButton btnCambiarEstado = new JButton("Cambiar Estado");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");

        botones.add(btnCrear);
        botones.add(btnBorrar);
        botones.add(btnCambiarEstado);
        botones.add(btnMostrarTodos);

        mainPanel.add(botones, BorderLayout.SOUTH);

        add(mainPanel);

        // --- Button actions
        btnCrear.addActionListener(e -> crearTicket());
        btnBorrar.addActionListener(e -> borrarTicket());
        btnCambiarEstado.addActionListener(e -> cambiarEstado());
        btnMostrarTodos.addActionListener(e -> resetFiltros());

        // --- Double-click to change status
        tableTickets.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cambiarEstado();
                }
            }
        });
    }

    // --- Methods
    private void listarTickets() {
        tableModel.setRowCount(0);
        for (Ticket t : service.obtenerTickets()) {
            tableModel.addRow(new Object[]{t.getId(), t.getTitulo(), t.getPrioridad(), t.getEstado()});
        }
    }

    private void aplicarFiltros() {
        String texto = searchField.getText().trim().toLowerCase();
        EstadoTicket estadoFiltro = (EstadoTicket) filterEstado.getSelectedItem();
        Prioridad prioridadFiltro = (Prioridad) filterPrioridad.getSelectedItem();

        List<Ticket> ticketsFiltrados = service.obtenerTickets().stream()
                .filter(t -> (texto.isEmpty() || t.getTitulo().toLowerCase().contains(texto)
                        || t.getDescripcion().toLowerCase().contains(texto)))
                .filter(t -> (estadoFiltro == null || t.getEstado() == estadoFiltro))
                .filter(t -> (prioridadFiltro == null || t.getPrioridad() == prioridadFiltro))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (Ticket t : ticketsFiltrados) {
            tableModel.addRow(new Object[]{t.getId(), t.getTitulo(), t.getPrioridad(), t.getEstado()});
        }
    }

    private void resetFiltros() {
        searchField.setText("");
        filterEstado.setSelectedItem(null);
        filterPrioridad.setSelectedItem(null);
        listarTickets();
    }

    private void crearTicket() {
        String titulo = JOptionPane.showInputDialog(this, "Título del ticket:");
        if (titulo == null || titulo.isEmpty()) return;

        String descripcion = JOptionPane.showInputDialog(this, "Descripción del ticket:");
        if (descripcion == null || descripcion.isEmpty()) return;

        Prioridad prioridad = (Prioridad) JOptionPane.showInputDialog(this,
                "Seleccioná la prioridad:", "Prioridad",
                JOptionPane.QUESTION_MESSAGE, null, Prioridad.values(), Prioridad.MEDIA);

        if (prioridad == null) return;

        service.crearTicket(titulo, descripcion, prioridad);
        listarTickets();
    }

    private void borrarTicket() {
        int fila = tableTickets.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un ticket primero");
            return;
        }

        int id = (int) tableTickets.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres borrar el ticket #" + id + "?", "Confirmar borrado",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            service.borrarTicket(id);
            listarTickets();
        }
    }

    private void cambiarEstado() {
        int fila = tableTickets.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un ticket primero");
            return;
        }

        int id = (int) tableTickets.getValueAt(fila, 0);

        EstadoTicket nuevoEstado = (EstadoTicket) JOptionPane.showInputDialog(this,
                "Seleccioná el nuevo estado:", "Cambiar Estado",
                JOptionPane.QUESTION_MESSAGE, null, EstadoTicket.values(),
                tableTickets.getValueAt(fila, 3));

        if (nuevoEstado != null) {
            service.cambiarEstado(id, nuevoEstado);
            listarTickets();
        }
    }
}
