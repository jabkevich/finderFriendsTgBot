package bd.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BdFriendsConnection {

    static Connection conn = null;


    public static Connection getBdFriendsConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");


        String url = "jdbc:mysql://51.250.30.209";
        String dbName = "tgUsers";
        String username = "root";
        String password = "root";

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
            statement.execute("CREATE TABLE " + dbName +".User (username VARCHAR(255) PRIMARY KEY)");

            conn =  DriverManager.getConnection(url + "/" +dbName, username, password);

            return conn;

        }

    }
}
