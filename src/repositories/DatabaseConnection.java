package src.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

@SuppressWarnings("all")
public class DatabaseConnection {

    private static final String CONFIG_FILE = "../../resources/config.properties";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            InputStream input = DatabaseConnection.class.getResourceAsStream(CONFIG_FILE);
            props.load(input);

            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
