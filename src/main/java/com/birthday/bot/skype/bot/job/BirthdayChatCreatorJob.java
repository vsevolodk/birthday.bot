package com.birthday.bot.skype.bot.job;

import com.birthday.bot.skype.contact.ContactWithBDay;
import com.birthday.bot.skype.holder.SkypeHolder;
import com.birthday.bot.skype.settings.loader.BirthdayBotSettings;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.participants.info.Contact;
import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class checks every day nearest birthday days.
 * You may customize vicinity of checking.
 * Example: If bDay satisfies bDay - currentDate <= vicinity, then chat for this bDay will be created.
 *
 * Created by Vsevolod Kaimashnikov on 21.02.2016.
 */
public class BirthdayChatCreatorJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(BirthdayChatCreatorJob.class);

    private String topicString = " %s %s";

    private final int interval;

    public BirthdayChatCreatorJob() {
        interval = BirthdayBotSettings.getInstance().getConfiguration().getInterval().intValue();
        processDRChar();
    }

    private void processDRChar() {
        String dr = "ДР";;
        if ("Linux".equals(System.getProperty("oc.name"))) {
            topicString = dr + topicString;
        } else {
            try {
                topicString = new String(dr.getBytes("windows-1251")) + topicString;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("topicString init failed", e);
                topicString = dr + topicString;
            }
        }
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("BirthdayChatCreatorJob executing...");

        ChatRepository chatRepository = ChatRepository.getInstance();
        ContactRepository contactRepository = ContactRepository.getInstance();

        boolean emptyInterval = true;
        for (final ContactWithBDay contact : contactRepository.getAllContactWithBDays().values()) {
            if (isBirthdayComingSoon(contact) && !chatRepository.isChatExistForUser(contact)) {
                emptyInterval = false;
                ChatForBDay groupChat = null;
                try {
                    groupChat = createGroupChat(contact);
                } catch (ConnectionException e) {
                    LOGGER.error("Creating of group chat is failed", e);
                }
                if (groupChat != null) {
                    chatRepository.addChat(groupChat);
                } else {
                    LOGGER.error(
                            "Group creating failed for contact {}. Attempt will be in next iteration",
                            contact.getUsername()
                    );
                }
                try {
                    LOGGER.info("Waiting between chat creations");
                    TimeUnit.MINUTES.sleep(3);
                } catch (InterruptedException e) {
                    LOGGER.error("Waiting is interrupted", e);
                }
            }
        }

        if (emptyInterval) {
            LOGGER.info("During the following {} days there will be not birthdays", interval);
        }
    }

    private ChatForBDay createGroupChat(ContactWithBDay bDayHuman) throws ConnectionException {

        Skype skype = SkypeHolder.getSkype();
        ContactRepository contactRepository = ContactRepository.getInstance();

        List<ContactWithBDay> users = contactRepository.getUsersWithout(Collections.singletonList(bDayHuman));

        try {
            GroupChat groupChat = skype.createGroupChat(users.toArray(new Contact[users.size()]));

            String topic = String.format(
                    topicString,
                    bDayHuman.getTopicName(),
                    bDayHuman.getBirthDay().toString("dd.MM." + DateTime.now().getYear())
            );
            groupChat.setTopic(topic);
            return new ChatForBDay(groupChat, bDayHuman);
        } catch (ConnectionException e) {
            LOGGER.error("createGroupChat method failed", e);
        }

        return null;
    }

    private boolean isBirthdayComingSoon(final ContactWithBDay contact) {
        final DateTime nextBDay = contact.getBirthDay().withYear(DateTime.now().getYear());

        final Days days = Days.daysBetween(new DateTime().toLocalDate(), nextBDay.toLocalDate());
        final int countDays = days.getDays();
        return countDays > 0 && countDays <= interval;
    }
}
