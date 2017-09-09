package com.birthday.bot.skype.bot.job;

import com.birthday.bot.skype.chat.ChatForBDay;
import com.birthday.bot.skype.chat.ChatRepository;
import com.birthday.bot.skype.contact.ContactWithBDay;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PingChatJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingChatJob.class);

    public PingChatJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("PingChatJob executing...");

        final ChatRepository chatRepository = ChatRepository.getInstance();

        final List<ChatForBDay> chats = chatRepository.getChats();

        for (final ChatForBDay chat : chats) {
            ContactWithBDay contactWithBDay = chat.getContactWithBDay();
            DateTime birthDay = contactWithBDay.getBirthDay();

            final DateTime nextBDay = birthDay.withYear(DateTime.now().getYear());
            final Days days = Days.daysBetween(new DateTime().toLocalDate(), nextBDay.toLocalDate());

            try {
                chat.sendMessage(Message.fromHtml(getMessage(days.getDays())));
            } catch (ConnectionException e) {
                LOGGER.error("Ping chat " + chat.getIdentity() + " failed", e);
            }
        }
    }

    private String getMessage(int days) {
        if (days != 1) {
            return String.format("<b>%s days left</b>", days);
        } else {
            return String.format("<b>%s day left</b>", days);
        }
    }
}
