
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        inicializarHotel(hotel);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMenu();
            int opcion = leerEntero(scanner);

            switch (opcion) {
                case 1 ->
                    hotel.consultarEstado();
                case 2 -> {
                    System.out.print("Ingrese el número de habitación a reservar: ");
                    int num = leerEntero(scanner);
                    try {
                        hotel.reservarHabitacion(num);
                        System.out.println("Habitación reservada exitosamente.");
                    } catch (ReservaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.print("Ingrese el número de la habitación a liberar: ");
                    int num = leerEntero(scanner);
                    try {
                        hotel.liberarHabitacion(num);
                        System.out.println("Habitación liberada exitosamente.");
                    } catch (ReservaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void inicializarHotel(Hotel hotel) {
        // Cargar habitaciones desde la base de datos
        if (DatabaseManager.cargarHabitaciones().isEmpty()) {
            System.out.println("Inicializando habitaciones en la base de datos...");

            Habitacion h1 = new Simple(101);
            Habitacion h2 = new Doble(102);
            Habitacion h3 = new Suite(103);
            Habitacion h4 = new Simple(104);
            Habitacion h5 = new Doble(105);

            hotel.registrarHabitacion(h1);
            hotel.registrarHabitacion(h2);
            hotel.registrarHabitacion(h3);
            hotel.registrarHabitacion(h4);
            hotel.registrarHabitacion(h5);

            System.out.println("Habitaciones registradas correctamente.");
        } else {
            System.out.println("Habitaciones ya registradas en la base de datos.");
        }

        // Asegurar que el hotel tiene todas las habitaciones cargadas desde la BD
        hotel.actualizarDesdeBD();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Sistema de Reservas de Hotel ---");
        System.out.println("1. Consultar el listado de habitaciones");
        System.out.println("2. Reservar habitación");
        System.out.println("3. Liberar habitación");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Debe ingresar un número. Intente de nuevo: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
