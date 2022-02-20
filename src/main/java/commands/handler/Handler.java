package commands.handler;

import commands.handler.commands.AddUserCommand;
import commands.handler.commands.DelUserCommand;
import commands.handler.commands.GetUserCommand;

import java.util.Map;

import static java.util.Map.entry;

public class Handler {

    private Map<String, ICommand> commandsHandler = Map.ofEntries(
            entry("find_friend", new GetUserCommand()),
            entry("start", new AddUserCommand())
    );


}
