package com.birthday.bot.skype.bot.message.handler;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public abstract class CommandHandler {

    public abstract void handle(final MessageReceivedEvent messageReceivedEvent);

    protected ChatForBDay getChatForBDay(final MessageReceivedEvent messageReceivedEvent) {
        final String identity = messageReceivedEvent.getMessage().getChat().getIdentity();
        return ChatRepository.getInstance().getGroupChat(identity);
    }
}
