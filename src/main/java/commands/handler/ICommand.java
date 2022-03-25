package commands.handler;

import bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommand {
    public SendMessage handleCommand(Message message);
}
