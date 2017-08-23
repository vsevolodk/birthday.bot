package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.chat.ChatForBDay;

import java.util.List;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class ShowOptions extends CommandHandler {

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        final ChatForBDay chatForBDay = getChatForBDay(messageReceivedEvent);
        chatForBDay.getOptions();

        final List<String> options = chatForBDay.getOptions();
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
            e.printStackTrace();
        }
    }
}
