import db.connector.BdFriendsConnection;
import db.controller.UsersDAOIml;
import bot.Bot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static  void main(String[] args) throws TelegramApiException, SQLException, ClassNotFoundException {
        Connection connector = BdFriendsConnection.getBdFriendsConnection();
        UsersDAOIml usersController = new UsersDAOIml(connector);
        Bot.startBot(usersController);
    }

}


