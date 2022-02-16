package bd.connector;

import config.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BdFriendsConnection {

    static Connection conn = null;


    public static Connection getBdFriendsConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = Config.getConfigField("URL");
        String dbName = Config.getConfigField("DB_NAME");
        String username = Config.getConfigField("USERNAME");
        String password = Config.getConfigField("PASSWORD");


        try {
            if(conn == null) {
                conn = DriverManager.getConnection(url + "/" + dbName, username, password);
                return conn;
            }
            return conn;
        } catch (SQLException err) {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            statement.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            statement.execute("CREATE TABLE " + dbName +".User (id PRIMARY KEY, username VARCHAR(255))");

            conn =  DriverManager.getConnection(url + "/" +dbName, username, password);

            return conn;

        }

    }
}
