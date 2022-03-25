package bot;

import commands.handler.Commands;
import commands.handler.Handler;
import commands.handler.ICommand;
import db.controller.UsersDAOIml;
import db.data.User;
import config.Config;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Objects;
import java.util.Optional;


public class Bot  extends TelegramLongPollingBot {


    private Handler commandsHandler = null;



    private UsersDAOIml usersController = null;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage()){
            return;
        }
        if (!update.getMessage().hasText() || !update.getMessage().hasEntities()) {
            return;
        }
        handleMessage(update.getMessage());
    }


    private void updateUsernameIfChanged(Message message){
        String userId = message.getFrom().getId().toString();
        String userName = message.getFrom().getUserName();
        User user = usersController.getById( userId);
        if(!Objects.equals(user.getUsername(), message.getFrom().getUserName())){
            usersController.updateUsername(userId, userName);
        }
    }

    private Commands getCommand (Message message){

        Optional<MessageEntity> commandEntry = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

        if (commandEntry.isEmpty()) {
            return Commands.not_command;
        }

        final String textCommand = commandEntry.get().getText().substring(1);

        try {
            return Commands.valueOf(textCommand);
        } catch ( IllegalArgumentException e){
            return Commands.unknown;
        }
    }

    private boolean isACommand(Commands command){
        return command != Commands.not_command && command != Commands.unknown;
    }


    private void handleMessage(Message message) throws TelegramApiException {

        this.updateUsernameIfChanged(message);

        final Commands command = this.getCommand(message);

        if(this.isACommand(command)){
            ICommand commander = this.commandsHandler.makeCommand(command);
            execute(commander.handleCommand(message));
        }

    }


    public Bot (DefaultBotOptions options, UsersDAOIml usersController) {
        super(options);
        this.usersController = usersController;
        this.commandsHandler = new Handler(this, usersController);
    }




    @Override
    public String getBotUsername() {
        return Config.getConfigField("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return  Config.getConfigField("BOT_TOKEN");
    }


    public static void startBot(UsersDAOIml usersController) throws TelegramApiException {
        Bot bot = new Bot(new DefaultBotOptions(), usersController);
//        bot.execute(SendMessage.builder().chatId("140777525").text("Hello").build());

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
