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

    public boolean cambiarEstado(int id, EstadoTicket estado) {
        Ticket t = buscarPorId(id);
        if (t == null) {
            return false;
        }
        t.setEstado(estado);
        return true;
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
        return null; // return null instead of throwing
    }

    public List<Ticket> obtenerTickets() {
        return tickets;
    }

    public boolean borrarTicket(int id) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == id) {
                tickets.remove(i);
                return true;
            }
        }
        return false;
    }
    public List<Ticket> filtrarPorEstado(EstadoTicket estado) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getEstado() == estado) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public List<Ticket> filtrarPorPrioridad(Prioridad prioridad) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getPrioridad() == prioridad) {
                resultado.add(t);
            }
        }
        return resultado;
    }

}
