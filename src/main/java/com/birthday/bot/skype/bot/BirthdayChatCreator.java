package com.birthday.bot.skype.bot;

import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.user.Contact;
import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Collections;
import java.util.List;

/**
 * This class checks every day nearest birthday days.
 * You may customize vicinity of checking.
 * Example: If bDay satisfies bDay - currentDate <= vicinity, then chat for this bDay will be created.
 *
 * Created by Vsevolod Kaimashnikov on 21.02.2016.
 */
public class BirthdayChatCreator extends Thread {

    private final Skype skype;
    private final ContactRepository contactRepository;
    private final ChatRepository chatRepository;
    private final int interval = BirthdayBotSettings.getInstance().getConfiguration().getInterval().intValue();

    public BirthdayChatCreator(final Skype skype, final ContactRepository contactRepository, final ChatRepository chatRepository) {
        this.skype = skype;
        this.contactRepository = contactRepository;
        this.chatRepository = chatRepository;
    }

    public void run() {
        while (true) {

            for (final ContactWithBDay contact : contactRepository.getAllContactWithBDays().values()) {
                if (isBirthdayCommingSoon(contact) && !chatRepository.isChatExistForUser(contact)) {
                    ChatForBDay groupChat = null;
                    try {
                        groupChat = createGroupChat(contact);
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    }
                    chatRepository.addChat(groupChat);
                    
                }
            }

            try {
                Thread.sleep(24*60*60*1000); // 1 day
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ChatForBDay createGroupChat(ContactWithBDay bDayHuman) throws ConnectionException {
        List<ContactWithBDay> users = contactRepository.getUsersWithout(Collections.singletonList(bDayHuman));
        GroupChat groupChat = null;
        try {
            groupChat = skype.createGroupChat(users.toArray(new Contact[users.size()]));
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        String topic = String.format(
                "ÄÐ %s %s",
                bDayHuman.getTopicName(),
                bDayHuman.getBirthDay().toString("dd.MM." + DateTime.now().getYear())
        );
        if (groupChat != null) {
            groupChat.setTopic(topic);
        }

        return new ChatForBDay(groupChat, bDayHuman);
    }

    private boolean isBirthdayCommingSoon(final ContactWithBDay contact) {
        final DateTime nextBDay = contact.getBirthDay().withYear(DateTime.now().getYear());

        final Days days = Days.daysBetween(new DateTime().toLocalDate(), nextBDay.toLocalDate());
        final int countDays = days.getDays();
        return countDays > 0 && countDays <= interval;
    }
}
