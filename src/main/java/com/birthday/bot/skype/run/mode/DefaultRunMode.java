package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.job.BotJobManager;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class DefaultRunMode extends AbstractRunMode {

    @Override
    public void run() {
        initSkype();
        final Skype skype = SkypeHolder.getSkype();
        listenerRegister.process(skype);
        subscribe(skype);

        BotJobManager.initScheduler();

        System.out.println("Birthday bot is running...");
    }

    private void subscribe(final Skype skype) {
        try {
            skype.subscribe();
        } catch (final ConnectionException e) {
            System.out.println("Skype subscribing for listener is failed");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
