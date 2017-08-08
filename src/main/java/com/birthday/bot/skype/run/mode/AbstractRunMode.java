package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.bot.listener.ListenerRegister;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;

import java.io.Console;

public abstract class AbstractRunMode implements RunMode {

    protected static BirthdayBot settings = BirthdayBotSettings.getInstance().getConfiguration();
    protected static ListenerRegister listenerRegister = new ListenerRegister();

    protected void initSkype() {
        System.out.println("skype initialization...");
        final String login = settings.getLogin();
        final String password = readPassword();
        SkypeHolder.init(login, password);
        System.out.println("skype initialization is success");
    }

    private String readPassword() {
        final Console console = System.console();
        System.out.print("Enter password:");
        char[] password = console.readPassword();
        return new String(password);
    }
}
