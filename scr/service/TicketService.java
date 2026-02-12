package service;

import model.Ticket;
import model.EstadoTicket;
import model.Prioridad;

import java.util.ArrayList;
import java.util.List;

public class TicketService {

    private List<Ticket> tickets = new ArrayList<>();

    public void agregarTicket(Ticket ticket) {
        tickets.add(ticket);
    }
    public void crearTicket(String titulo, String descripcion, Prioridad prioridad) {
        Ticket t = new Ticket(titulo, descripcion, prioridad);
        tickets.add(t);
    }

    public List<Ticket> listarTickets() {
        return tickets;
    }

    public void cambiarEstado(int id, EstadoTicket estado) {
        Ticket t = buscarPorId(id);
        t.setEstado(estado);
    }

    public List<String> verHistorial(int id) {
        Ticket t = buscarPorId(id);
        return t.getHistorial();
    }

    private Ticket buscarPorId(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new RuntimeException("Ticket no encontrado");
    }
}
