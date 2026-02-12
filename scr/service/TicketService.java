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