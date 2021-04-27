package application;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DOA {

    protected Connection connection;

    public DOA() throws Exception {

        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\Claudio\\Desktop\\Java\\WeightApp\\sql\\database.properties"));

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String dburl = properties.getProperty("dburl");

        connection = DriverManager.getConnection(dburl, user, password);
    }
}
