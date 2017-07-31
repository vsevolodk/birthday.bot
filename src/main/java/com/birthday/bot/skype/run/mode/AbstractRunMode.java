package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.bot.listener.ListenerRegister;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

import java.io.Console;

public abstract class AbstractRunMode implements RunMode {

    protected static BirthdayBot settings = BirthdayBotSettings.getInstance().getConfiguration();
    protected static ListenerRegister listenerRegister = new ListenerRegister();

    protected void initSkype() {
        System.out.println("skype initialization...");
        final String password = readPassword();
        final Skype skype = new SkypeBuilder(settings.getLogin(), password).withAllResources().build();

        try {
            System.out.println("try skype login...");
            skype.login();
        } catch (InvalidCredentialsException | ConnectionException | NotParticipatingException e) {
            System.out.println("Skype login failed");
            e.printStackTrace();
            System.exit(0);
        }
        SkypeHolder.setInstance(skype);
        System.out.println("skype initialization is success");
    }

    private String readPassword() {
        final Console console = System.console();
        System.out.print("Enter password:");
        char[] password = console.readPassword();
        return new String(password);
    }
}
