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
            Properties props = new Properties();
            InputStream input = DatabaseConnection.class.getResourceAsStream(CONFIG_FILE);
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
