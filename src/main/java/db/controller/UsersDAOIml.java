package db.controller;

import db.data.User;

import java.sql.*;
import java.util.List;

public class UsersDAOIml implements UsersDAO {

    private Connection connection = null;
    private Statement statement = null;

    private void makeARequest(final String request) {
        try {
            statement.execute(request);
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    private ResultSet makeARequestAndGetResponse(final String request) {
        try {
            return statement.executeQuery(request);
        }catch (SQLException err){
            err.printStackTrace();
            return null;
        }
    }


    public UsersDAOIml(Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
    }




    @Override
    public List<User> UsersList() {
        return null;
    }

    @Override
    public void add(String username, String id)  {
        final String request = String.format("INSERT INTO User(id, username) VALUES ('%s', '%s')", id,username);
        makeARequest(request);
    }

    @Override
    public void delete(User user) throws SQLException {
        final String request = String.format("DELETE FROM User WHERE id '%s'", user.getUsername());
        makeARequest(request);
    }

    @Override
    public void edit(User user) {
        //TODO
    }

    private User getUser(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()){
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setId(resultSet.getString("id"));
            }
            return user;
        }catch (SQLException err) {
            err.printStackTrace();
            return null;
        }
    }



    @Override
    public User getById(String id) {
        final String request = String.format("SELECT username, id FROM User where id = %s", id);
        return getUser(makeARequestAndGetResponse(request));
    }


    @Override
    public User getByUsername(String username) {
        final String request = String.format("SELECT username, id FROM User where username = %s", username);
        return getUser(makeARequestAndGetResponse(request));
    }

    @Override
    public User getRandomUser() {
        //WHERE username <> '' AND username <> '' в будущем планируется исключать уже предложенных пользователей
        final String request = "SELECT id, username FROM User ORDER BY RAND() LIMIT 1;";
        return getUser(makeARequestAndGetResponse(request));
    }

    @Override
    public int getCountOfRecords() {
        try{
            final String request = "SELECT COUNT(*) as total FROM User;";
            ResultSet resultSet = statement.executeQuery(request);
            return resultSet.getInt("total");
        } catch (SQLException err) {
            err.printStackTrace();
            return 0;
        }
    }

    public void updateUsername(String id, String newUsername) {
        try{
            final String request =  String.format("UPDATE User SET username='%s' WHERE id = %s;", newUsername, id);
            statement.execute(request);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
