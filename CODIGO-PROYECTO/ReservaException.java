public class ReservaException extends Exception { //manejar errores relacionados únicamente con las reservas.
    public ReservaException(String mensaje) {
        super(mensaje); // Llama al constructor de Exception y guarda el mensaje del error.
    }
}
