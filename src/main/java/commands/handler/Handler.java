package commands.handler;

import bot.Bot;
import commands.handler.commands.AddUserCommand;
import commands.handler.commands.DelUserCommand;
import commands.handler.commands.GetUserCommand;
import db.controller.UsersDAOIml;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class Handler {


    private  Map<Commands, ICommand> commandsHandler = null;

    public Handler(Bot bot, UsersDAOIml usersController) {
        this.commandsHandler = Map.ofEntries(
                entry(Commands.find_friend, new GetUserCommand(bot, usersController)),
                entry(Commands.start, new AddUserCommand(bot, usersController))
        );
    }

    public ICommand makeCommand(Commands command) {

            return commandsHandler.get(command);

    }

}
