package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.impl.AddedOption;
import com.birthday.bot.skype.bot.message.handler.impl.DeleteChat;
import com.birthday.bot.skype.bot.message.handler.impl.ShowOptions;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class CommandHandlerStrategy {

    private static final Map<String, CommandHandler> strategies = new HashMap<String, CommandHandler>(10, 1) {{
        put("\\boption", new AddedOption());
        put("\\bdone", new DeleteChat());
        put("\\bshow", new ShowOptions());
    }};

    public static void handle(final MessageReceivedEvent event) {
        final String mes = event.getMessage().getContent().asPlaintext();
        final StringTokenizer tokenizer = new StringTokenizer(mes, " ");
        final String command = tokenizer.nextToken();
        strategies.get(command).handle(event);
    }
}
