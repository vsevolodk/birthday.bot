package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;

public abstract class AbstractRunMode implements RunMode {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRunMode.class);

    protected static BirthdayBot settings = BirthdayBotSettings.getInstance().getConfiguration();

    protected void initSkype() {
        LOGGER.info("skype initialization...");
        final String login = settings.getLogin();
        final String password = readPassword();
        SkypeHolder.init(login, password);
        LOGGER.info("skype initialization is success");
    }

    private String readPassword() {
        final Console console = System.console();
        System.out.print("Enter password:");
        char[] password = console.readPassword();
        return new String(password);
    }
}
