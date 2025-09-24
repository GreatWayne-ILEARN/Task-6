import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {

    // Database connection 
    private static final String URL = "jdbc:postgresql://localhost:5432/flexi";
    private static final String USER = "postgres";   
    private static final String PASSWORD = "blaqbonze";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to PostgreSQL database!");

            // Insert example
            insertUser(conn, "John", "Doe", "123 Street", "Engineer", "2025-09-22");

            // Update example
            updateUserOccupation(conn, 1, "Software Developer");

            // Delete example
            deleteUser(conn, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert user
    public static void insertUser(Connection conn, String firstname, String lastname, String address, String occupation, String date) throws SQLException {
        String sql = "INSERT INTO users (firstname, lastname, address, occupation, created_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, address);
            stmt.setString(4, occupation);
            stmt.setDate(5, java.sql.Date.valueOf(date)); // Convert String → SQL Date
            stmt.executeUpdate();
            System.out.println("User inserted successfully!");
        }
    }

    // Update user
    public static void updateUserOccupation(Connection conn, int id, String newOccupation) throws SQLException {
        String sql = "UPDATE users SET occupation = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newOccupation);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("User updated successfully!");
        }
    }

    // Delete user
    public static void deleteUser(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("User deleted successfully!");
        }
    }

}
