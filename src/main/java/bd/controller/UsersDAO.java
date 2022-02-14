package bd.controller;

import bd.data.User;

import java.sql.SQLException;
import java.util.List;

public interface UsersDAO {
    List<User> UsersList();
    void add(String username, String id) throws SQLException;
    void delete(User user) throws SQLException;
    void edit(User user);
    User getById(String id);
    User getByUsername(String username);
    User getRandomUser() throws SQLException;
    int getCountOfRecords() ;
}
