package com.birthday.bot.skype.bot.listener;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.chat.messages.ReceivedMessage;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import com.birthday.bot.skype.bot.message.CommandHandlerStrategy;

/**
 * Created by Vsevolod on 21.02.2016.
 */
public class BirthdayChatListener implements Listener{

    @EventHandler
    public void onMessage(MessageReceivedEvent e) {

        Chat chat = e.getChat();
        if (chat instanceof GroupChat) {

            ReceivedMessage message = e.getMessage();
            Message content = message.getContent();
            String plaintext = content.asPlaintext();

            if (plaintext.startsWith("\\")) {
                CommandHandlerStrategy.handle(e);
            }
        }
    }
}
