package service;

import model.Ticket;
import model.EstadoTicket;
import model.Prioridad;

import java.util.*;
import java.util.stream.Collectors;

public class TicketService {

    // Use Map for fast lookups
    private Map<Integer, Ticket> ticketsMap = new HashMap<>();

    // --- CRUD Operations
    public void crearTicket(String titulo, String descripcion, Prioridad prioridad) {
        Ticket t = new Ticket(titulo, descripcion, prioridad);
        ticketsMap.put(t.getId(), t);
    }

    public boolean cambiarEstado(int id, EstadoTicket estado) {
        Ticket t = ticketsMap.get(id);
        if (t == null) return false;
        t.setEstado(estado);
        return true;
    }

    public boolean borrarTicket(int id) {
        return ticketsMap.remove(id) != null;
    }

    public List<Ticket> obtenerTickets() {
        return new ArrayList<>(ticketsMap.values());
    }

    public List<Ticket> filtrarPorEstado(EstadoTicket estado) {
        return ticketsMap.values().stream()
                .filter(t -> t.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public List<Ticket> filtrarPorPrioridad(Prioridad prioridad) {
        return ticketsMap.values().stream()
                .filter(t -> t.getPrioridad() == prioridad)
                .collect(Collectors.toList());
    }

    public List<String> verHistorial(int id) {
        Ticket t = ticketsMap.get(id);
        if (t == null) return new ArrayList<>();
        return t.getHistorial();
    }

    public Ticket buscarPorId(int id) {
        return ticketsMap.get(id);
    }
}
