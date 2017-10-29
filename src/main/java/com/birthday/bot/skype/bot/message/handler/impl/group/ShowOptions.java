package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.chat.ChatForBDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class ShowOptions extends CommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowOptions.class);

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        final ChatForBDay chatForBDay = getChatForBDay(messageReceivedEvent);
        chatForBDay.getOptions();

        final List<String> options = chatForBDay.getOptions();
        if (options.isEmpty()) {
            try {
                chatForBDay.sendMessage("You don't have options for current chat");
                return;
            } catch (ConnectionException e) {
                LOGGER.error("Errod during sending response for empty option list", e);
            }
        }

        final StringBuilder targetMes = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            targetMes
                    .append(i + 1)
                    .append(" ")
                    .append(options.get(i))
                    .append('\n');
        }

        try {
            chatForBDay.sendMessage(targetMes.toString());
        } catch (ConnectionException e) {
            LOGGER.error("Errod during sending response for option list", e);
        }
    }
}
