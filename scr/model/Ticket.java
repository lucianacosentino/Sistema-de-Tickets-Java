package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private List<String> historial;

    public Ticket(int id, String titulo, String descripcion, Prioridad prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = EstadoTicket.ABIERTO;
        this.historial = new ArrayList<>();
        historial.add("Ticket creado en estado ABIERTO");
    }


    public int getId() {
        return id;
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

    public List<String> getHistorial() {
        return historial;
    }
}