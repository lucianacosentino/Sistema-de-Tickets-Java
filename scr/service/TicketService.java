package service;

import model.Ticket;
import model.EstadoTicket;
import model.Prioridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketService {

    private static final String FILE_NAME = "tickets.dat";
    private List<Ticket> tickets;

    public TicketService() {
        tickets = cargar();
    }

    public void crearTicket(String titulo, String descripcion, Prioridad prioridad) {
        Ticket t = new Ticket(titulo, descripcion, prioridad);
        tickets.add(t);
        guardar();
    }

    public List<Ticket> obtenerTickets() {
        return tickets;
    }

    public Ticket buscarPorId(int id) {
        return tickets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean cambiarEstado(int id, EstadoTicket estado) {
        Ticket t = buscarPorId(id);
        if (t == null) return false;

        t.setEstado(estado);
        guardar();
        return true;
    }

    public boolean borrarTicket(int id) {
        boolean eliminado = tickets.removeIf(t -> t.getId() == id);
        if (eliminado) guardar();
        return eliminado;
    }

    private void guardar() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Ticket> cargar() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            List<Ticket> lista = (List<Ticket>) ois.readObject();

            // Ajustar contador para evitar IDs duplicados
            int maxId = lista.stream()
                    .mapToInt(Ticket::getId)
                    .max()
                    .orElse(0);

            actualizarContador(maxId + 1);

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void actualizarContador(int nuevoValor) {
        try {
            var field = Ticket.class.getDeclaredField("contador");
            field.setAccessible(true);
            field.set(null, nuevoValor);
        } catch (Exception ignored) {}
    }
}
