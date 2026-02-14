package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int contador = 1;

    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private List<String> historial;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Ticket(String titulo, String descripcion, Prioridad prioridad) {
        this.id = contador++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = EstadoTicket.ABIERTO;
        this.historial = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();

        historial.add("Ticket creado");
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public EstadoTicket getEstado() { return estado; }
    public Prioridad getPrioridad() { return prioridad; }
    public List<String> getHistorial() { return historial; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
        historial.add("Estado cambiado a " + estado);
    }

    @Override
    public String toString() {
        return "Ticket #" + id +
                " | " + titulo +
                " | Prioridad: " + prioridad +
                " | Estado: " + estado;
    }
}
