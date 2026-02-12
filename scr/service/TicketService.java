package service;

import model.Ticket;
import model.Prioridad;
import model.EstadoTicket;

import java.util.ArrayList;
import java.util.List;

public class TicketService {

    private List<Ticket> tickets;
    private int contadorId;

    public TicketService() {
        tickets = new ArrayList<>();
        contadorId = 1;
    }

    public void crearTicket(String titulo, String descripcion, Prioridad prioridad) {
        Ticket ticket = new Ticket(contadorId++, titulo, descripcion, prioridad);
        tickets.add(ticket);
    }

    public List<Ticket> listarTickets() {
        return tickets;
    }

    public Ticket buscarPorId(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public void cambiarEstado(int id, EstadoTicket nuevoEstado) {
        Ticket ticket = buscarPorId(id);
        if (ticket != null) {
            ticket.cambiarEstado(nuevoEstado);
        }
    }
}
