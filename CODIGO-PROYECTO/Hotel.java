
import java.util.List;

public class Hotel {

    private List<Habitacion> habitaciones;

    public Hotel() {
        DatabaseManager.inicializarBaseDeDatos();
        habitaciones = DatabaseManager.cargarHabitaciones();
    }

    public void actualizarDesdeBD() {
        habitaciones = DatabaseManager.cargarHabitaciones();
    }

    public void registrarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        DatabaseManager.guardarHabitacion(habitacion);
    }

    public void reservarHabitacion(int numero) throws ReservaException {
        Habitacion habitacion = buscarHabitacion(numero);
        habitacion.reservar();
        DatabaseManager.guardarHabitacion(habitacion);
    }

    public void liberarHabitacion(int numero) throws ReservaException {
        Habitacion habitacion = buscarHabitacion(numero);
        habitacion.liberar();
        DatabaseManager.guardarHabitacion(habitacion);
    }

    public List<Habitacion> consultarEstado() {
        for (Habitacion habitacion : habitaciones) {
            System.out.printf("Habitación %d - Tipo: %s - Estado: %s%n",
                    habitacion.getNumero(),
                    habitacion.getTipo(),
                    habitacion.isOcupada() ? "Ocupada" : "Disponible");
        }
        return habitaciones;
    }

    private Habitacion buscarHabitacion(int numero) throws ReservaException {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        throw new ReservaException("Habitación con número " + numero + " no encontrada.");
    }
}
