package bot;

import commands.handler.Handler;
import commands.handler.ICommand;
import commands.handler.commands.AddUserCommand;
import commands.handler.commands.GetUserCommand;
import db.controller.UsersDAOIml;
import db.data.User;
import config.Config;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Map.entry;


public class Bot  extends TelegramLongPollingBot {


    private Handler commandsHandler = null;



    private UsersDAOIml usersController = null;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            handleMessage(update.getMessage());
        }
    }

    public void adviseTheUser(Message message)  {

        User user = usersController.getRandomUser();

        System.out.println(user.getUsername());

        try {
            execute(
                    SendMessage
                            .builder()
                            .text("Попробуй написать ему: " + "@" + user.getUsername())
                            .chatId(message.getChatId().toString())
                            .build()
            );
        } catch (TelegramApiException ignored) {

        }

    }

    public void addUser (@NotNull Message message) {
        usersController.add(message.getFrom().getUserName().toString(), message.getFrom().getId().toString());
    }



    private void handleMessage(Message message) throws TelegramApiException {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntry =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

            if (commandEntry.isEmpty()) {
                return;
            }

            String userId = message.getFrom().getId().toString();
            String userName = message.getFrom().getUserName();
            User user = usersController.getById( userId);

            System.out.println(userName);
            System.out.println(user.getUsername());
           if(!Objects.equals(user.getUsername(), message.getFrom().getUserName())){
                usersController.updateUsername(userId, userName);
            }

            String command = message.getText().substring(commandEntry.get().getOffset(), commandEntry.get().getLength());

           System.out.println(command);
            ICommand commander =  this.commandsHandler.makeCommand(command);

            if (commander != null){
                commander.handleCommand(this, message);
            }

        }
    }
    public Bot (DefaultBotOptions options, UsersDAOIml usersController) {
        super(options);
        this.usersController = usersController;
        this.commandsHandler = new Handler();
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
