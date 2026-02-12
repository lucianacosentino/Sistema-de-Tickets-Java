# Sistema de Gestión de Tickets (Java)

Proyecto simple en Java que implementa un **sistema de gestión de tickets** con **interfaz gráfica**, organizado por capas (modelo, servicio y UI).

## 📁 Estructura del proyecto

├── Main.java

├── model/
│ ├── Ticket.java
│ └── EstadoTicket.java
├── service/
│ └── TicketService.java
└── ui/
└── MainWindow.java

## 🧩 Descripción de capas

### `model`
Contiene las clases del dominio:
- `Ticket`: representa un ticket.
- `EstadoTicket`: enum con los posibles estados del ticket.

### `service`
Contiene la lógica del sistema:
- `TicketService`: maneja la creación, búsqueda y cambio de estado de los tickets.

### `ui`
Interfaz gráfica (Swing):
- `MainWindow`: ventana principal del sistema.  
  Desde acá se interactúa con el usuario (botones, combos, etc.).

### `Main.java`
Clase principal del programa.  
Inicializa el servicio y lanza la interfaz gráfica.
