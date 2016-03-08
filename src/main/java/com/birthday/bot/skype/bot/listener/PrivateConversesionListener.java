package com.birthday.bot.skype.bot.listener;

import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

/**
 * Created by Vsevolod on 21.02.2016.
 */
public class PrivateConversesionListener implements Listener {

    @EventHandler
    public void onMessage(MessageReceivedEvent e) {
        System.out.println("Got message: " + e.getMessage().getContent());
    }
}
