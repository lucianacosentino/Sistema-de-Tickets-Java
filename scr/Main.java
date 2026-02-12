import model.Prioridad;
import model.EstadoTicket;
import service.TicketService;
import ui.MainWindow;
import java.util.Scanner;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {

        // Look & Feel moderno
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus");
        }

        TicketService service = new TicketService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Cómo querés ejecutar el sistema?");
        System.out.println("1 - Interfaz gráfica");
        System.out.println("2 - Terminal");
        System.out.print("Opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            SwingUtilities.invokeLater(() ->
                    new MainWindow(service).setVisible(true)
            );
        } else {
            ejecutarCLI(scanner, service);
        }

        scanner.close();
    }


    // --------- MODO TERMINAL ----------
    private static void ejecutarCLI(Scanner scanner, TicketService service) {
        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE TICKETS =====");
            System.out.println("1. Crear ticket");
            System.out.println("2. Listar tickets");
            System.out.println("3. Cambiar estado");
            System.out.println("4. Ver historial");
            System.out.println("5. Borrar ticket");
            System.out.println("0. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> crearTicket(scanner, service);
                case 2 -> listarTickets(service);
                case 3 -> cambiarEstado(scanner, service);
                case 4 -> verHistorial(scanner, service);
                case 5 -> borrarTicket(scanner, service);
            }

        } while (opcion != 0);
    }

    // --------- MÉTODOS AUXILIARES ----------
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
        EstadoTicket estado = EstadoTicket.valueOf(scanner.nextLine().toUpperCase());

        service.cambiarEstado(id, estado);
        System.out.println("Estado actualizado.");
    }

    private static void verHistorial(Scanner scanner, TicketService service) {
        System.out.print("ID del ticket: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        service.verHistorial(id).forEach(System.out::println);
    }

    private static void borrarTicket(Scanner scanner, TicketService service) {
        System.out.print("ID del ticket: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (service.borrarTicket(id)) {
            System.out.println("Ticket borrado 🗑️");
        } else {
            System.out.println("No existe ese ticket.");
        }
    }
}
