package com.birthday.bot.skype.bot.listener;

import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

public class PrivateConversationListener implements Listener {

    @EventHandler
    public void onMessage(MessageReceivedEvent e) {
        System.out.println("Got message: " + e.getMessage().getContent());
    }
}
