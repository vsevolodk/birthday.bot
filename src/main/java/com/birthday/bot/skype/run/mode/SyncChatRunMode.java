package com.birthday.bot.skype.run.mode;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.Console;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SyncChatRunMode extends AbstractRunMode {

    @Override
    public void run() {
        initSkype();
        final Skype skype = SkypeHolder.getSkype();
        final Console console = System.console();
        System.out.println("Enter count of chats:");
        final Integer count = Integer.valueOf(console.readLine());
        final Set<Chat> chats = new HashSet<Chat>(loadMoreChats(skype, count));
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

    private List<Chat> loadMoreChats(Skype skype, Integer count) {
        try {
            return skype.loadMoreChats(count);
        } catch (ConnectionException e) {
            System.out.println("Loading of chats is failed");
            e.printStackTrace();
            System.exit(0);
        }
        throw new IllegalStateException("method loadMoreChats finished incorrectly");
    }
}
