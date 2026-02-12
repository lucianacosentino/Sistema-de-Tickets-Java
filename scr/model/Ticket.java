package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private static int contador = 1;

    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private List<String> historial;

    public Ticket(String titulo, String descripcion, Prioridad prioridad) {
        this.id = contador++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = EstadoTicket.ABIERTO;
        this.historial = new ArrayList<>();
        historial.add("Ticket creado");
    }


    public int getId() {
        return id;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoTicket nuevoEstado) {
        this.estado = nuevoEstado;
        historial.add("Estado cambiado a " + nuevoEstado);
    }

    public String toString() {
        return "Ticket #" + id +
                " | " + titulo +
                " | Prioridad: " + prioridad +
                " | Estado: " + estado;
    }
    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
        historial.add("Estado cambiado a " + estado);
    }

    public List<String> getHistorial() {
        return historial;
    }
}
