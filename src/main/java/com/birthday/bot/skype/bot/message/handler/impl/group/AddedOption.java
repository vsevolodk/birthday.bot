package com.birthday.bot.skype.bot.message.handler.impl.group;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.birthday.bot.skype.bot.message.handler.CommandHandler;
import com.birthday.bot.skype.chat.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handle request on add present option
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public class AddedOption extends CommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddedOption.class);

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        final String plaintext = messageReceivedEvent.getMessage().getContent().asPlaintext();

        //5 length of \boption, because start index is 4
        final String option = plaintext.substring(8, plaintext.length());

        ChatRepository chatRepository = ChatRepository.getInstance();
        ChatForBDay chatForBDay = getChatForBDay(messageReceivedEvent);
        final String identity = chatForBDay.getIdentity();

        chatRepository.addOptionForChat(identity, option);
        LOGGER.debug("Option {} is added for chat {}", option, identity);
    }
}
