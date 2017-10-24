package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.impl.group.*;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.birthday.bot.skype.bot.message.CommandsConstants.*;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class CommandHandlerStrategy {

    private static final Map<String, CommandHandler> strategies = new HashMap<String, CommandHandler>(4, 1) {{
        put(HELP, new HelpHandler());
        put(OPTION, new AddedOption());
        put(DONE, new DeleteChat());
        put(SHOW_OPTIONS, new ShowOptions());
        put(ADD_HISTORY_GIFT, new AddHistoryGiftForContactHandler());
        put(SHOW_HISTORY_GIFT, new ShowHistoryGiftHandler());
    }};

    public static void handle(final MessageReceivedEvent event) {
        final String mes = event.getMessage().getContent().asPlaintext();
        final StringTokenizer tokenizer = new StringTokenizer(mes, " ");
        final String command = tokenizer.nextToken();
        final CommandHandler commandHandler = strategies.get(command);
        if (commandHandler != null) {
            commandHandler.handle(event);
        } else {
            //todo: incorrect command message to client
        }
    }
}
