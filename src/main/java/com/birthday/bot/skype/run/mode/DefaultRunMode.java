package com.birthday.bot.skype.run.mode;

import com.birthday.bot.db.NitriteHolder;
import com.birthday.bot.skype.bot.listener.Subscriber;
import com.birthday.bot.skype.job.BotJobManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRunMode extends AbstractRunMode {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    @Override
    public void run(String[] args) {
        initSkype();
        Subscriber.process();
        NitriteHolder.init();
        BotJobManager.initScheduler();

        LOGGER.info("Birthday bot is running...");
    }
}
