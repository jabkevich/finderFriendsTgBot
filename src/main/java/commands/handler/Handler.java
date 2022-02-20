package commands.handler;

import bot.Bot;
import commands.handler.commands.AddUserCommand;
import commands.handler.commands.DelUserCommand;
import commands.handler.commands.GetUserCommand;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class Handler {

    private Map<String, ICommand> commandsHandler = Map.ofEntries(
            entry("/find_friend", new GetUserCommand()),
            entry("/start", new AddUserCommand())
    );

    public ICommand makeCommand(String string) {
        try {
            return commandsHandler.get(string);
        } catch (NullPointerException ex){
            ex.getMessage();
            return null;
        }
    }

}
