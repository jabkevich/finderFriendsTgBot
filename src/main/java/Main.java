import bd.connector.BdFriendsConnection;
import bd.controller.UsersDAOIml;
import bot.Bot;
import config.Config;
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


