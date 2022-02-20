package db.connector;

import config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public  class  BdFriendsConnection {

    static Connection conn = null;


    public static Connection getBdFriendsConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = Config.getConfigField("URL");
        String username = Config.getConfigField("USERNAME");
        String password = Config.getConfigField("PASSWORD");
        String dbName = "tgUsers";

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
            statement.execute("CREATE TABLE `" + dbName +"`.`User` (id INT PRIMARY KEY, username VARCHAR(255))");


            conn =  DriverManager.getConnection(url + "/" +dbName, username, password);

            return conn;

        }

    }
}
