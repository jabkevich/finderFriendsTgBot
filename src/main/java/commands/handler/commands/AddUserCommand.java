package commands.handler.commands;

import bot.Bot;
import commands.handler.ICommand;
import db.controller.UsersDAOIml;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AddUserCommand implements ICommand {

    private Bot bot = null;
    private UsersDAOIml usersController = null;

    public AddUserCommand(Bot bot, UsersDAOIml usersController){
        this.bot = bot;
        this.usersController = usersController;
    }

    @Override
    public SendMessage handleCommand(Message message) {
        usersController.add(message.getFrom().getUserName().toString(), message.getFrom().getId().toString());
        return SendMessage.builder().build();
    }
}
