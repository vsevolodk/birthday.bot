package com.birthday.bot.skype;

import com.birthday.bot.skype.bot.BirthdayChatCreator;
import com.birthday.bot.skype.bot.listener.ListenerRegister;
import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.settings.BirthdayBot;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.exceptions.*;
import com.birthday.bot.skype.bot.PingRunner;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.holder.SkypeHolder;

import java.io.Console;
import java.io.IOException;
import java.util.*;

/**
 * Main class for running bot
 * Created by Vsevolod on 19.02.2016.
 */
public class BirthDaySkypeBotRunner {

    private static BirthdayBot settings = BirthdayBotSettings.getInstance().getConfiguration();
    private static ListenerRegister listenerRegister = new ListenerRegister();

    public static void main(String[] args) throws ConnectionException, ChatNotFoundException, ParseException, IOException, InvalidCredentialsException {
        if (args.length > 0) {
            String arg = args[0];
            switch (arg) {
                case "contactsAdding": {
                    contactsAdding();
                    break;
                }
                case "syncChats": {
                    syncChats();
                    break;
                }
                default: {
                }
            }
        } else {
            runMainLogic();
        }
    }

    private static void syncChats() throws ConnectionException, NotParticipatingException, InvalidCredentialsException {
        initSkype();
        final Skype skype = SkypeHolder.getSkype();
        final Console console = System.console();
        System.out.println("Enter count of chats:");
        final Integer count = Integer.valueOf(console.readLine());
        final Set<Chat> chats = new HashSet<Chat>(skype.loadMoreChats(count));
        chats.addAll(skype.getAllChats());

        System.out.println("Loaded " + chats.size() + " chats");

        final Map<String, ContactWithBDay> allContactWithBDays = ContactRepository.getInstance().getAllContactWithBDays();
        for (Chat chat : chats) {
            if (chat instanceof GroupChat) {
                final GroupChat groupChat = (GroupChat) chat;
                final String topic = groupChat.getTopic();
                System.out.println("Topic: " + topic);
                System.out.println("Continue? y/n");
                String ans = console.readLine();
                if ("y".equals(ans)) {
                    final ChatRepository chatRepository = ChatRepository.getInstance();
                    for (ContactWithBDay contactWithBDay : allContactWithBDays.values()) {
                        if (topic.contains(contactWithBDay.getTopicName())
                                && !chatRepository.isChatExistForUser(contactWithBDay)) {
                            final ChatForBDay chatForBDay = new ChatForBDay(groupChat, contactWithBDay);
                            chatRepository.addChat(chatForBDay);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("syncChats finished");
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

    private static void runMainLogic() throws ConnectionException, ChatNotFoundException, InvalidCredentialsException, ParseException, IOException {
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
