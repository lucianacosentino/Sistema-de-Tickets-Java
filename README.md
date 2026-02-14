# 🎫 Sistema de Gestión de Tickets (Java Swing)

Aplicación de escritorio desarrollada en **Java + Swing** para la gestión de tickets con persistencia local en archivo.

---
## 👩‍💻 Autora

**Luciana Cosentino**

Proyecto académico / práctica de arquitectura Java con Swing.

## 🚀 Características

- ✅ Crear tickets
- ✅ Listar tickets
- ✅ Cambiar estado (**ABIERTO, EN_PROGRESO, CERRADO**)
- ✅ Borrar tickets
- ✅ Filtrar por estado
- ✅ Filtrar por prioridad
- ✅ Buscar por texto
- ✅ Ver descripción completa del ticket
- ✅ Ver historial de cambios
- ✅ Persistencia automática en disco
- ✅ Interfaz gráfica con `JTable`

---

## 🧠 Arquitectura del Proyecto

├── Main.java
├── model/
│ ├── Ticket.java
│ ├── EstadoTicket.java
│ └── Prioridad.java
├── service/
│ └── TicketService.java
├── ui/
│ └── MainWindow.java
└── tickets.dat (se genera automáticamente)

### 📦 Capas

- **model** → Entidades del dominio
- **service** → Lógica de negocio y persistencia
- **ui** → Interfaz gráfica (Swing)
- **Main** → Punto de entrada

---

## 💾 Persistencia

La aplicación guarda automáticamente los tickets en:

tickets.dat


- Se crea automáticamente si no existe
- Se actualiza cada vez que se crea, modifica o elimina un ticket
- Los datos se mantienen aunque cierres la aplicación

---

## 🛠 Requisitos

- **Java 17 o superior**

Verificar versión:

```bash
java -version
