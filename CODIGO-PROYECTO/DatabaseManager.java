
import java.sql.*; //importa la libreria para trabjar con la base de datos
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:hotel.db"; //ruta base de datos

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC"); // Carga manual del driver
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo cargar el driver JDBC de SQLite.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBaseDeDatos() { //creacion de la tabla
        String createTable = "CREATE TABLE IF NOT EXISTS habitaciones ("
                + "numero INTEGER PRIMARY KEY, "
                + "tipo TEXT, "
                + "ocupada TEXT)";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) { //crea la tabla usando statement
            stmt.execute(createTable);  // Ahora solo crea la tabla si no existe, sin borrar datos
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    public static void guardarHabitacion(Habitacion habitacion) {
        String sql = "INSERT OR REPLACE INTO habitaciones (numero, tipo, ocupada) VALUES (?, ?, ?)"; //Inserta o actualiza una habitación en la base de datos.
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) { //PreparedStatement protege la consulta SQL al separar los datos de la instrucción SQL.
            pstmt.setInt(1, habitacion.getNumero());
            pstmt.setString(2, habitacion.getTipo());
            pstmt.setString(3, habitacion.isOcupada() ? "Ocupada" : "Disponible");
            pstmt.executeUpdate(); //Sustituye los ? por los valores reales.Envía la consulta a la base de datos y la ejecuta.
        } catch (SQLException e) {
            System.out.println("Error al guardar habitacion: " + e.getMessage());
        }
    }

    public static List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>(); //Crea una lista de habitaciones .
        String sql = "SELECT numero, tipo, ocupada FROM habitaciones"; //y prepara la consulta SQL

        //Llama al método connect(), que abre una conexión con la base de datos. conn (es esa conexion), crea un objeto stament para enviar consultas a la bd
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) { //ResultSet objeto que devuelve resultados. rs conjunto de resultados(tabla)
            while (rs.next()) { //Recorrer cada fila del resultado

                int numero = rs.getInt("numero");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("ocupada");  //Permite convertir una cadena (String) en un valor lógico (boolean).
                boolean ocupada = estado.equals("Ocupada");

                Habitacion habitacion = switch (tipo) { // Crea un objeto Habitacion dependiendo del tipo. Usa switch para determinar si es Simple, Doble o Suite.
                    case "Simple" ->
                        new Simple(numero);
                    case "Doble" ->
                        new Doble(numero);
                    case "Suite" ->
                        new Suite(numero);
                    default ->
                        null;
                };

                if (habitacion != null) { //Si la habitación existe, 

                    if (ocupada) {
                        habitacion.reservarSinExcepcion(); //y la marca como ocupada si es necesario.
                    }
                    habitaciones.add(habitacion); //la agrega a la lista 
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar habitaciones: " + e.getMessage());
        }
        return habitaciones; //Retorna la lista con todas las habitaciones cargadas.
    }
}
