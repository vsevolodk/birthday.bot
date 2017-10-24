package com.birthday.bot.skype.bot.message.handler;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vsevolod Kaimashnikov on 06.03.2016.
 */
public abstract class CommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHandler.class);

    public abstract void handle(final MessageReceivedEvent messageReceivedEvent);

    protected ChatForBDay getChatForBDay(final MessageReceivedEvent messageReceivedEvent) {
        final String identity = messageReceivedEvent.getMessage().getChat().getIdentity();
        return ChatRepository.getInstance().getGroupChat(identity);
    }

    protected List<String> getParameters(final MessageReceivedEvent messageReceivedEvent) {
        final String message = messageReceivedEvent.getMessage().getContent().asPlaintext();
        final String[] split = message.split(" ");
        final List<String> parameters = new ArrayList<>(split.length - 1);
        parameters.addAll(Arrays.asList(split).subList(1, split.length));
        return parameters;
    }

    protected String getSkypeLogin(MessageReceivedEvent messageReceivedEvent) {
        final String identity = messageReceivedEvent.getMessage().getChat().getIdentity();
        final ChatForBDay groupChat = ChatRepository.getInstance().getGroupChat(identity);
        return groupChat.getContactWithBDay().getUsername();
    }

    protected void sendResponse(MessageReceivedEvent messageReceivedEvent, Message message) {
        try {
            messageReceivedEvent.getMessage().getChat().sendMessage(message);
        } catch (ConnectionException e) {
            LOGGER.error("Error during send response", e);
        }
    }
}
