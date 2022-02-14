package bot;

import bd.controller.UsersDAOIml;
import bd.data.User;
import config.Config;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;


public class Bot  extends TelegramLongPollingBot {



    private UsersDAOIml usersController = null;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){

            handleMessage(update.getMessage());
        }
    }


    private void handleMessage(Message message) throws TelegramApiException {
        if(message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntry =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntry.isPresent()){
                String command = message.getText().substring(commandEntry.get().getOffset(), commandEntry.get().getLength());
                switch (command) {
                    case "/find_friend":
                        User user =  usersController.getRandomUser();

                        execute(
                                SendMessage
                                        .builder()
                                        .text("Попробуй написать ему: " + "@" + user.getUsername())
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                    case "/start":
                        usersController.add(message.getFrom().getUserName().toString());
                        return;

                }
            }
        }
    }
    public Bot (DefaultBotOptions options, UsersDAOIml usersController) {
        super(options);
        this.usersController = usersController;
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
