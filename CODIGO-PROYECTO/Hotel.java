
import java.util.List;

public class Hotel {

    private List<Habitacion> habitaciones;

    public Hotel() { //Cada vez que se crea un objeto Hotel, se asegura que la base de datos esté lista y se cargan las habitaciones existentes.
        DatabaseManager.inicializarBaseDeDatos();
        habitaciones = DatabaseManager.cargarHabitaciones();
    }

    public void actualizarDesdeBD() { //actualiza la lista de habitaciones
        habitaciones = DatabaseManager.cargarHabitaciones();
    }

    public void registrarHabitacion(Habitacion habitacion) { 
        habitaciones.add(habitacion); // Añade una habitación nueva a la lista habitaciones
        DatabaseManager.guardarHabitacion(habitacion); //Guarda la nueva habitación en la base de datos 
    }

    public void reservarHabitacion(int numero) throws ReservaException {
        Habitacion habitacion = buscarHabitacion(numero);//Busca una habitación específica por número.
        habitacion.reservar(); //Llama al método reservar() de la habitación.
        DatabaseManager.guardarHabitacion(habitacion); //Guarda de la base de datos
    }

    public void liberarHabitacion(int numero) throws ReservaException {
        Habitacion habitacion = buscarHabitacion(numero);
        habitacion.liberar(); //lo que marca la habitación como disponible.
        DatabaseManager.guardarHabitacion(habitacion);
    }

    public List<Habitacion> consultarEstado() { //Recorre todas las habitaciones y muestra su estado
        for (Habitacion habitacion : habitaciones) { //Para cada objeto habitacion dentro de la lista habitaciones, haz lo siguiente..
            System.out.printf("Habitación %d - Tipo: %s - Estado: %s%n", //los simbolos son tipos de datos
                    habitacion.getNumero(), //objeto.metodo
                    habitacion.getTipo(),
                    habitacion.isOcupada() ? "Ocupada" : "Disponible");
        }
        return habitaciones; //Devuelve la lista de habitaciones.
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
