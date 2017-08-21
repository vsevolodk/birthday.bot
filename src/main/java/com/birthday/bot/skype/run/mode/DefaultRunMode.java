package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.bot.listener.Subscriber;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.job.BotJobManager;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRunMode extends AbstractRunMode {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    @Override
    public void run() {
        initSkype();
        Subscriber.process();
        BotJobManager.initScheduler();

        LOGGER.info("Birthday bot is running...");
    }
}
