import javax.swing.*;
import service.TicketService;
import ui.MainWindow;

public class Main {

    public static void main(String[] args) {

        // Look & Feel moderno (Nimbus)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            TicketService service = new TicketService();
            new MainWindow(service).setVisible(true);
        });
    }


}
