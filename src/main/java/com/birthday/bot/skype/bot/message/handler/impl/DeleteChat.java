package com.birthday.bot.skype.bot.message.handler.impl;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.chat.ChatRepository;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class DeleteChat extends CommandHandler {

    @Override
    public void handle(final MessageReceivedEvent messageReceivedEvent) {
        final Chat chat = messageReceivedEvent.getChat();
        ChatRepository chatRepository = ChatRepository.getInstance();
        chatRepository.deleteChat(chat.getIdentity());
    }
}
