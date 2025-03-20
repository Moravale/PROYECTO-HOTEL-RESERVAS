
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:hotel.db";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC"); // Carga manual del driver
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo cargar el driver JDBC de SQLite.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBaseDeDatos() {
        String createTable = "CREATE TABLE IF NOT EXISTS habitaciones ("
                + "numero INTEGER PRIMARY KEY, "
                + "tipo TEXT, "
                + "ocupada TEXT)";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);  // Ahora solo crea la tabla si no existe, sin borrar datos
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    public static void guardarHabitacion(Habitacion habitacion) {
        String sql = "INSERT OR REPLACE INTO habitaciones (numero, tipo, ocupada) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, habitacion.getNumero());
            pstmt.setString(2, habitacion.getTipo());
            pstmt.setString(3, habitacion.isOcupada() ? "Ocupada" : "Disponible");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar habitacion: " + e.getMessage());
        }
    }

    public static List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT numero, tipo, ocupada FROM habitaciones";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int numero = rs.getInt("numero");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("ocupada"); // Ahora almacena "Ocupada" o "Disponible"
                boolean ocupada = estado.equals("Ocupada");

                Habitacion habitacion = switch (tipo) {
                    case "Simple" ->
                        new Simple(numero);
                    case "Doble" ->
                        new Doble(numero);
                    case "Suite" ->
                        new Suite(numero);
                    default ->
                        null;
                };

                if (habitacion != null) {
                    if (ocupada) {
                        habitacion.reservarSinExcepcion();
                    }
                    habitaciones.add(habitacion);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar habitaciones: " + e.getMessage());
        }
        return habitaciones;
    }
}
