package com.birthday.bot.skype;

import com.birthday.bot.skype.bot.BirthdayChatCreator;
import com.birthday.bot.skype.bot.listener.ListenerRegister;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.exceptions.*;
import com.birthday.bot.skype.bot.PingRunner;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.holder.SkypeHolder;

import java.io.Console;
import java.io.IOException;
import java.util.Collection;

/**
 * Main class for running bot
 * Created by Vsevolod on 19.02.2016.
 */
public class BirthDaySkypeBotRunner {

    private static BirthdayBot settings = BirthdayBotSettings.getInstance().getConfiguration();
    private static ListenerRegister listenerRegister = new ListenerRegister();

    public static void main(String[] args) throws ConnectionException, ChatNotFoundException, ParseException, IOException, InvalidCredentialsException {
        if (args.length > 0 && "contactsAdding".equals(args[0])) {
            contactsAdding();
        } else {
            runMainLogic();
        }
    }

    private static void contactsAdding() throws NotParticipatingException, InvalidCredentialsException {
        try {
            initSkype();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        ContactRepository contactRepository = ContactRepository.getInstance();
        Collection<ContactWithBDay> contacts = contactRepository.getAllContactWithBDays().values();

        for (ContactWithBDay contact : contacts) {
            try {
                if (!contact.isAuthorized()) {
                    try {
                        contact.sendRequest(BirthdayBotSettings.getInstance().getConfiguration().getSendRequestMessage());
                        System.out.println(String.format("For %s request for adding is send", contact.getUsername()));
                    } catch (ConnectionException e) {
                        System.out.println(
                                String.format(
                                        "For user %s - %s",
                                        contact.getUsername(),
                                        e.getMessage()
                                )
                        );
                    }
                }
            } catch (NoSuchContactException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Contact adding finished");
    }

    private static void runMainLogic()  throws ConnectionException, ChatNotFoundException, InvalidCredentialsException, ParseException, IOException {
        initSkype();
        final Skype skype = SkypeHolder.getSkype();
        listenerRegister.process(skype);
        skype.subscribe();

        final BirthdayChatCreator birthdayChatCreator = new BirthdayChatCreator(
                skype,
                ContactRepository.getInstance(),
                ChatRepository.getInstance()
        );
        final PingRunner pingRunner = new PingRunner();

        birthdayChatCreator.start();
        pingRunner.start();

        System.out.println("Birthday bot is running...");
    }

    private static void initSkype() throws ConnectionException, NotParticipatingException, InvalidCredentialsException {
        final String password = readPassword();
        final Skype skype = new SkypeBuilder(settings.getLogin(), password).withAllResources().build();
        skype.login();
        SkypeHolder.setInstance(skype);
    }

    private static String readPassword() {
        final Console console = System.console();
        System.out.print("Enter password:");
        char[] password = console.readPassword();
        return new String(password);
    }
}
