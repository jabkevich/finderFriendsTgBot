package commands.handler.commands;

import bot.Bot;
import commands.handler.ICommand;
import org.telegram.telegrambots.meta.api.objects.Message;

public class GetUserCommand implements ICommand  {


    @Override
    public void handleCommand(Bot bot, Message message) {
       bot.adviseTheUser(message);
    }
}
