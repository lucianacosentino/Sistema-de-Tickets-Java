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
