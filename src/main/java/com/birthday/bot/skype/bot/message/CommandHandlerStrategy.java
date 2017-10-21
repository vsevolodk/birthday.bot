package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.impl.group.AddedOption;
import com.birthday.bot.skype.bot.message.handler.impl.group.DeleteChat;
import com.birthday.bot.skype.bot.message.handler.impl.group.HelpHandler;
import com.birthday.bot.skype.bot.message.handler.impl.group.ShowOptions;
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
