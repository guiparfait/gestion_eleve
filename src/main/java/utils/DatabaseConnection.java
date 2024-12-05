package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_ecole";
    private static final String USER = "root"; //
    private static final String PASSWORD = "09210035Gui@"; //

    @org.jetbrains.annotations.Nullable
    public static Connection getConnection() {
        //test
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            return null;
        }
    }
}
