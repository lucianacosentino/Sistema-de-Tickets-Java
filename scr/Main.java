import model.*;
import service.TicketService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TicketService service = new TicketService();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE TICKETS =====");
            System.out.println("1. Crear ticket");
            System.out.println("2. Listar tickets");
            System.out.println("3. Cambiar estado de un ticket");
            System.out.println("4. Ver historial de un ticket");
            System.out.println("5. Borrar ticket");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    crearTicket(scanner, service);
                    break;
                case 2:
                    listarTickets(service);
                    break;
                case 3:
                    cambiarEstado(scanner, service);
                    break;
                case 4:
                    verHistorial(scanner, service);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void crearTicket(Scanner scanner, TicketService service) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Prioridad (BAJA, MEDIA, ALTA): ");
        Prioridad prioridad = Prioridad.valueOf(scanner.nextLine().toUpperCase());

        service.crearTicket(titulo, descripcion, prioridad);
        System.out.println("Ticket creado correctamente.");
    }
    private static void listarTickets(TicketService service) {
        service.listarTickets().forEach(System.out::println);
    }

    private static void cambiarEstado(Scanner scanner, TicketService service) {
        System.out.print("ID del ticket: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo estado (ABIERTO / EN_PROGRESO / CERRADO): ");
        String estadoStr = scanner.nextLine().toUpperCase();

        try {
            EstadoTicket estado = EstadoTicket.valueOf(estadoStr);
            service.cambiarEstado(id, estado);
            System.out.println("Estado actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido.");
        }
    }


    private static void verHistorial(Scanner scanner, TicketService service) {
        System.out.print("ID del ticket: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        service.verHistorial(id).forEach(System.out::println);
    }

}
