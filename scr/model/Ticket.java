package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    private static final AtomicInteger contador = new AtomicInteger(1);

    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private List<String> historial;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Ticket(String titulo, String descripcion, Prioridad prioridad) {
        this.id = contador.getAndIncrement();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = EstadoTicket.ABIERTO;
        this.historial = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Ticket creado: " + fechaCreacion);
    }

    // --- Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public EstadoTicket getEstado() { return estado; }
    public List<String> getHistorial() { return historial; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    // --- Setters / Updaters
    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Estado cambiado a " + estado + " en " + fechaActualizacion);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Título actualizado a '" + titulo + "' en " + fechaActualizacion);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Descripción actualizada en " + fechaActualizacion);
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Prioridad cambiada a " + prioridad + " en " + fechaActualizacion);
    }

    @Override
    public String toString() {
        return "Ticket #" + id +
                " | " + titulo +
                " | Prioridad: " + prioridad +
                " | Estado: " + estado;
    }
}
