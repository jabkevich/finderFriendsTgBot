package commands.handler.commands;

import bot.Bot;
import commands.handler.ICommand;
import db.controller.UsersDAOIml;
import db.data.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class GetUserCommand implements ICommand  {
    private Bot bot = null;
    private UsersDAOIml usersController = null;

    public GetUserCommand(Bot bot, UsersDAOIml usersController){
        this.bot = bot;
        this.usersController = usersController;
    }

    @Override
    public SendMessage handleCommand(Message message) {
//
//        bot.adviseTheUser(message);


        User user = usersController.getRandomUser();

        return SendMessage
                .builder()
                .text("Попробуй написать ему: " + "@" + user.getUsername())
                .chatId(message.getChatId().toString())
                .build();

    }
}
