package com.birthday.bot.skype.bot.message.handler.impl;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.chat.ChatRepository;

/**
 * This class handle request on add present option
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class AddedOption extends CommandHandler {

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        final String plaintext = messageReceivedEvent.getMessage().getContent().asPlaintext();

        //5 length of \boption, because start index is 4
        final String option = plaintext.substring(8, plaintext.length());

        ChatRepository chatRepository = ChatRepository.getInstance();
        ChatForBDay chatForBDay = getChatForBDay(messageReceivedEvent);

        chatRepository.addOptionForChat(chatForBDay.getIdentity(), option);
    }
}
