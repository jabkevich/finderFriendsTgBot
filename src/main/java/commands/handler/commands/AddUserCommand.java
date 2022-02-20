package commands.handler.commands;

import bot.Bot;
import commands.handler.ICommand;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AddUserCommand implements ICommand {
    @Override
    public void handleCommand(Bot bot, Message message) {
        bot.addUser(message);
    }
}
