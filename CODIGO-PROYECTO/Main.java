
import java.util.Scanner; //leer la entra del usuario(libreria)

public class Main {

    public static void main(String[] args) {
        Hotel hotel = new Hotel(); //se crea el objeto de la clase hotel, nos permite acceder a sus metodos y atributos
        inicializarHotel(hotel);//carga las habitaciones y se pasa el objeto como argumento

        Scanner scanner = new Scanner(System.in); //poder leer la entrada del usuario

        while (true) { //bucle 
            mostrarMenu(); //imprime las opciones
            int opcion = leerEntero(scanner); //lee la opcion 

            switch (opcion) {
                case 1 ->
                    hotel.consultarEstado(); //llama al metodo del objeto hotel 
                case 2 -> {
                    System.out.print("Ingrese el número de habitación a reservar: ");
                    int num = leerEntero(scanner);
                    try {
                        hotel.reservarHabitacion(num);
                        System.out.println("Habitación reservada exitosamente.");
                    } catch (ReservaException e) { //e contiene la informacion del error con el mensaje
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
        // se asegura que el hotel tenga habitaciones registradas en la base de datos
        if (DatabaseManager.cargarHabitaciones().isEmpty()) { //.isEmpty()//Comprueba si la lista está vacía
            System.out.println("Inicializando habitaciones en la base de datos...");

            Habitacion h1 = new Simple(101);
            Habitacion h2 = new Doble(102);
            Habitacion h3 = new Suite(103); //Si la base de datos está vacía, el programa creará                       
            Habitacion h4 = new Simple(104);
            Habitacion h5 = new Doble(105);

            hotel.registrarHabitacion(h1);
            hotel.registrarHabitacion(h2);
            hotel.registrarHabitacion(h3);
            hotel.registrarHabitacion(h4); //y guardar nuevas habitaciones  en la base de datos y en la lista del hotel
            hotel.registrarHabitacion(h5); //hotel es el objeto de la clase Hotel, y estamos llamando a sus métodos para manipular sus datos.



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

    private static int leerEntero(Scanner scanner) { //static:se puede llamar sin crear objetos 
        while (!scanner.hasNextInt()) { // Verifica si el siguiente valor ingresado es un número entero.
            System.out.print("Debe ingresar un número. Intente de nuevo: ");
            scanner.next(); // Descarta la entrada incorrecta y espera un nuevo valor.
        }
        return scanner.nextInt(); //Cuando el usuario ingresa un número válido, se captura y retorna.
    }
}
