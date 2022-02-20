package commands.handler;

import bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommand {
    public void handleCommand(Bot bot, Message message);
}
