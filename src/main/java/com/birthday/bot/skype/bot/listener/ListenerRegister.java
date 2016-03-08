package com.birthday.bot.skype.bot.listener;

import com.samczsun.skype4j.Skype;

/**
 * Class for registration listeners
 *
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class ListenerRegister {

    public void process(final Skype skype) {
        skype.getEventDispatcher().registerListener(new BirthdayChatListener());
    }
}
