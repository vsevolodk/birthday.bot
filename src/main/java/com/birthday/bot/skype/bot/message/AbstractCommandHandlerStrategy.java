package com.birthday.bot.skype.bot.message;

import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.bot.message.handler.impl.group.*;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static com.birthday.bot.skype.bot.message.CommandsConstants.*;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public abstract class AbstractCommandHandlerStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandHandlerStrategy.class);

    public void handle(final MessageReceivedEvent event) {
        final String command = getCommand(event);
        final CommandHandler commandHandler = getStrategies().get(command);
        if (commandHandler != null) {
            commandHandler.handle(event);
        } else {
            final Chat chat = event.getMessage().getChat();
            try {
                chat.sendMessage(Message.fromHtml("Unknown command. See available via \\bhelp"));
            } catch (ConnectionException e) {
                LOGGER.error("Error during send unknow command message", e);
            }
        }
    }

    protected abstract Map<String, CommandHandler> getStrategies();

    private String getCommand(final MessageReceivedEvent event) {
        final String mes = event.getMessage().getContent().asPlaintext();
        final StringTokenizer tokenizer = new StringTokenizer(mes, " ");
        final String command = tokenizer.nextToken().toLowerCase();
        return command;
    }
}
