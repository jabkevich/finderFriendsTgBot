package commands.handler.commands;

import bot.Bot;
import commands.handler.ICommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DelUserCommand implements ICommand {

    @Override
    public SendMessage handleCommand(Message message) {
        return SendMessage.builder().build();
    }
}
